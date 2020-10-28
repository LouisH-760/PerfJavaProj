import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Thread-oriented TCP Server, meant primarily for reception of data
 * @author Louis Hermier
 *
 */
public class TCPServer {
	private final int PORT = 25642;
	
	private ServerSocket servSock;
	private Socket sock;
	private String received;
	
	public volatile List<Message> haystack;
	public volatile boolean cont;
	/**
	 * ctor
	 * Create the server socket that will be used to create sockets when a client connects
	 * @throws IOException
	 */
	public TCPServer() throws IOException {
		System.out.println("Starting on " + PORT);
		servSock = new ServerSocket(PORT);
		cont = true;
		haystack = new ArrayList<Message>();
	}
	
	/**
	 * Await a connection, answer the question and close it
	 * then do it again
	 * @throws IOException
	 */
	public void listen() throws IOException {
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
	}
}

