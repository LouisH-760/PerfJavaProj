import java.io.IOException;
import java.util.Date;

/**
 * Logic of the Sensor
 * @author Louis Hermier, Arno Chaidron
 *
 */
public class SensorLogic implements Runnable{
	
	private final String ACK = "ACK";
	
	private String productId;
	private String vendorId;
	private String location;
	
	private TCPReceiver receiver;
	private Thread t_receiver;
	private ReceivedMessage tmp_msg;
	
	private Message draft;
	private Thread t_sender;
	
	private TCPPeriodicSender pSender;
	private Thread t_pSender;
	
	public volatile boolean cont;
	
	private SensorGUI gui;
	private Thread t_gui;
	
	private int delay;
	private boolean pSenderRunning;
	
	/**
	 * Constructor
	 * @param vendorId  : VendorId of the sensor
	 * @param productId : ProductID of the sensor
	 * @param location : Location of the sensor
	 * @throws IOException : when the TCPReceiver cannot start
	 */
	public SensorLogic(String vendorId, String productId, String location) throws IOException {
		setVendorId(vendorId);
		setProductId(productId);
		setLocation(location);
		
		receiver = new TCPReceiver(TCPCommon.SENSOR_PORT);
		t_receiver = new Thread(receiver);
		
		cont = true;
		
		gui = new SensorGUI();
		t_gui = new Thread(gui);
		
		pSenderRunning = false;
	}
	
	/**
	 * Start the receiver, await requests and send replies
	 */
	@Override
	public void run() {
		// @TODO: move to separate function (init()?) just like close()
		t_receiver.start();
		t_gui.start();
		while(cont) {
			if(!receiver.haystack.isEmpty()) {
				tmp_msg = receiver.haystack.pop();
				
				if(tmp_msg.getType().equals(Message.TYPE_STOP)) {
					draft = buildReply(ACK);
					if(pSenderRunning) {
						pSender.cont = false;
						pSenderRunning = false;
					}
					t_sender = new Thread(new TCPThrowawaySender(tmp_msg.getAddress(), TCPCommon.STATION_PORT, draft));
					t_sender.start();
					// wait for the answer to complete before resuming.
					try {
						t_sender.join();
					} catch (Exception e) {
						System.err.println("From SensorLogic: Interrupted while sending a reply");
					}
				} else if(tmp_msg.getType().equals(Message.TYPE_INFO)) {
					draft = buildReply(this.toString());
					t_sender = new Thread(new TCPThrowawaySender(tmp_msg.getAddress(), TCPCommon.STATION_PORT, draft));
					t_sender.start();
					// wait for the answer to complete before resuming.
					try {
						t_sender.join();
					} catch (Exception e) {
						System.err.println("From SensorLogic: Interrupted while sending a reply");
					}
				} else if(tmp_msg.getType().equals(Message.TYPE_DATA)) {
					// if requesting data, body of the message should be the delay!
					delay = Helper.str2int(tmp_msg.getContents());
					pSender = new TCPPeriodicSender(tmp_msg.getAddress(), TCPCommon.STATION_PORT, delay, this.toString(), () -> measure());
					t_pSender = new Thread(pSender);
					t_pSender.start();
					pSenderRunning = true;
				}
			}
		}
		close();
	}
	
	/**
	 * Close all that needs to be closed
	 * (for now, just the receiver)
	 */
	public void close() {
		receiver.cont = false;
		gui.cont = false;
		if(pSenderRunning)
			pSender.cont = false;
	}
	
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String toString() {
		return "ProductID: " + productId + Message.WEAK_SEP + "VendorID: " + vendorId + Message.WEAK_SEP + "Location: " + location;
	}
	
	/**
	 * Build a reply Message from the given string
	 * @param content: body of the message
	 * @return : Message
	 */
	private Message buildReply(String content) {
		return new Message(Message.TYPE_REPL, this.toString(), content);
	}
	
	private String measure() {
		String time = new Date().toString();
		return new Temperature(time, gui.temp).toString();
	}
}
