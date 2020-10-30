import java.util.function.Supplier;

/**
 * Send a message every x amount of time
 * @author Louis Hermier
 *
 */
public class TCPPeriodicSender implements Runnable{
	private Supplier<String> contentAction;
	private String address;
	private String id;
	private int port;
	private int delay;
	
	private Thread t_sender;
	
	public volatile boolean cont;
	
	public TCPPeriodicSender(String address, int port, int delay, String id, Supplier<String> messageAction) {
		this.contentAction = messageAction;
		this.address = address;
		this.port = port;
		this.delay = delay;
		this.id = id;
		cont = true;
	}

	@Override
	public void run() {
		while(cont) {
			t_sender = new Thread(new TCPThrowawaySender(address, port, buildMessage()));
			try {
				t_sender.join();
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private Message buildMessage() {
		return new Message(Message.TYPE_REPL, id, contentAction.get());
	}
}
