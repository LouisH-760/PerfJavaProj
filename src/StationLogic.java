import java.io.IOException;
import java.util.Iterator;

public class StationLogic implements Runnable{

	private int port;
	
	private TCPReceiver receiver;
	private Thread t_receiver;
	private ReceivedMessage tmp_msg;
	
	private Message draft;
	private Thread t_sender;
	public volatile boolean cont;
	
	public StationLogic() throws IOException {
		receiver = new TCPReceiver(TCPCommon.STATION_PORT);
		t_receiver = new Thread(receiver);
		cont = true;
	}
	
	@Override
	public void run() {
		t_receiver.start();
		draft = new Message(Message.TYPE_DATA, "127.0.0.1", "1000");
		t_sender = new Thread(new TCPThrowawaySender("127.0.0.1", TCPCommon.SENSOR_PORT, draft));
		t_sender.start();
		while(cont) {
			if(!receiver.haystack.isEmpty()) {
				Iterator<ReceivedMessage> bob = receiver.haystack.iterator();
				while(bob.hasNext()) {
					tmp_msg = bob.next();
					bob.remove();
					System.out.println(tmp_msg);
				}
					
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		receiver.cont = false;
	}

}
