import java.io.IOException;
import java.net.Socket;

/**
 * One-time-use meant, thread oriented TCP Sender
 * @author Louis Hermier
 *
 */
public class TCPThrowawaySender implements Runnable{
	private String address;
	private int port;
	private Message message;
	
	private Socket sock;
	
	/**
	 * Create the throwaway sender using a port, address and message
	 * @param address : String  : ip to send to
	 * @param port    : Int     : port to send on
	 * @param message : Message : message to send
	 */
	public TCPThrowawaySender(String address, int port, Message message) {
		this.address = address;
		this.port = port;
		this.message = message;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// Obtain a new socket
			sock = TCPCommon.newConnectedClientSocket(address, port);
			// Send the message over it
			TCPCommon.sendWithSocket(message.toString(), sock);
			// Destroy the socket
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
