import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Thread-oriented TCP Server, meant primarily for reception of data
 * while running, all received Message objects will be appended to the public volatile haystack ArrayList
 * change the public volatile boolean cont to false to stop
 * @author Louis Hermier
 *
 */
public class TCPServer implements Runnable{
	
	private ServerSocket servSock;
	private Socket sock;
	private String received;
	
	public volatile List<Message> haystack;
	public volatile boolean cont;
	/**
	 * ctor
	 * Create the server socket that will be used to create sockets when a client connects
	 * @param port: Port to start the server on.
	 * @throws IOException
	 */
	public TCPServer(int port) throws IOException {
		// Start a server socket on the given port
		servSock = new ServerSocket(port);
		cont = true;
		haystack = new ArrayList<Message>();
	}
	
	/**
	 * Await a connection, answer the question and close it
	 * then do it again
	 * @throws IOException
	 */
	public void run() {
		try {
		// this has to be closed from the outside
			while(cont) {
				// Accept connections on the server
				sock = servSock.accept();
				// print a line whenever a connection is received
				System.out.println("connection from: " + sock.getInetAddress());
				// Actually receive data
				// Packet size is a constant in TCPCommon
				received = TCPCommon.receiveFromSocket(sock);
				// Try to convert the received text into a message
				try {
					haystack.add(new Message(received));
				} catch (Exception e) {
					System.err.println("Invalid Message Received");
				}
				// Close the connection
				sock.close();
			}
			// Close the server socket
			servSock.close();
		} catch (IOException ioe) {
			System.err.println("IO Problem");
		} finally {
			cont = false;
		}
	}
}

