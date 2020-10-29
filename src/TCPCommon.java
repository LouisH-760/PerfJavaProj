import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Common functions for TCP Networking
 * @author Louis Hermier
 *
 */
public class TCPCommon {
	// Size (Bytes) of the packets to send
	public static final int BUF_SIZE = 4096;
	public static final int TIMEOUT = 5000;
	
	/**
	 * Return a client socket already connected to a server for direct use
	 * @param adress : address to connect to
	 * @param port : port to connect to
	 * @param timeout : timeout in ms
	 * @return connected socket
	 * @throws IOException
	 */
	public static Socket newConnectedClientSocket(String adress, int port, int timeout) throws IOException {
		Socket sock = new Socket();
		sock.connect(new InetSocketAddress(adress, port), timeout);
		return sock;
	}
	
	/**
	 * Use the inputStream of the passed socket to receive data with a given buffer size
	 * @param sock : socket to receive from
	 * @param bufferSize : size (in bytes) of the buffer
	 * @return String of the received data
	 * @throws IOException
	 */
	public static String receiveFromSocket(Socket sock) throws IOException {
		byte[] buffer = new byte[BUF_SIZE];
		InputStream listener = sock.getInputStream();
		int amountBytes = listener.read(buffer);
		
		if(amountBytes > BUF_SIZE)
			throw new IOException("Packet too big");
		
		return new String(buffer).trim();
	}
	
	/**
	 * Send a string using a given socket and buffer size
	 * @param msg : message to send
	 * @param sock : socket to send through
	 * @param bufsize : size (in bytes) of the buffer
	 * @throws IOException
	 */
	public static void sendWithSocket(String msg, Socket sock) throws IOException {
		byte[] buf = string2Buf(msg);
		OutputStream out = sock.getOutputStream();
		out.write(buf);
	}
	
	/**
	 * convert to string to a buffer (byte array) of a given size
	 * @param str : string to convert
	 * @param bufSize : size of the buffer
	 * @return byte[bufSize] holding the string
	 */
	public static byte[] string2Buf(String str) {
		byte[] buffer = new byte[BUF_SIZE];
		buffer = str.getBytes();
		return buffer;
	}
}
