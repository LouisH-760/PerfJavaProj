import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Thread-oriented TCP Server, meant primarily for reception of data
 * while running, all received Message objects will be appended to the public volatile haystack ArrayList
 * change the public volatile boolean cont to false to stop
 * @author Louis Hermier
 *
 */
public class TCPReceiver implements Runnable{
	
	// Private variables
	private ServerSocket servSock;
	private Socket sock;
	private String received;
	// temp variables
	private ReceivedMessage tmpMessage;
	
	// Thread-safe public variables
	public volatile ConcurrentLinkedDeque<ReceivedMessage> haystack;
	public volatile boolean cont;
	
	/**
	 * ctor
	 * Create the server socket that will be used to create sockets when a client connects
	 * @param port: Port to start the server on.
	 * @throws IOException
	 */
	public TCPReceiver(int port) throws IOException {
		// Start a server socket on the given port
		servSock = new ServerSocket(port);
		cont = true;
		haystack = new ConcurrentLinkedDeque<ReceivedMessage>();
	}
	
	/**
	 * Await a connection, establish it, receive a Message
	 * throw it on top of the haystack and close the connection, ad vitam aeternam
	 */
	@Override
	public void run() {
		try {
		// this has to be closed from the outside
			while(cont) {
				// Accept connections on the server
				sock = servSock.accept();
				// print a line whenever a connection is received
				// Actually receive data
				// Packet size is a constant in TCPCommon
				received = TCPCommon.receiveFromSocket(sock);
				// Try to convert the received text into a message
				try {
					// Get the actual message
					tmpMessage = new ReceivedMessage(received);
					// Get the sender's IP to answer (if needed)
					// remove the leasing / that java likes to add somehow
					// otherwise the answer can't be sent, because the IP isn't recognized
					tmpMessage.setAddress(sock.getInetAddress().toString().substring(1));
					// Add the message to the haystack
					haystack.push(tmpMessage);
				} catch (Exception e) {
					System.err.println("Invalid Message Received");
					e.printStackTrace();
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

