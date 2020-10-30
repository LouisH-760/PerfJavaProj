import java.io.IOException;

public class StationLogic implements Runnable{

	private int port;
	
	private TCPReceiver receiver;
	private Thread t_receiver;
	private ReceivedMessage tmp_msg;
	
	private Message draft;
	private Thread t_sender;
	public volatile boolean cont;
	
	public StationLogic(int port) throws IOException {
		this.port = port;
		receiver = new TCPReceiver(port);
		t_receiver = new Thread(receiver);
		cont = true;
	}
	
	@Override
	public void run() {
		t_receiver.run();
		draft = new Message(Message.TYPE_DATA, "aa", "1000");
		t_sender = new Thread(new TCPThrowawaySender("127.0.0.1", port, draft));
		t_sender.run();
		while(cont) {
			if(!receiver.haystack.isEmpty()) {
				tmp_msg = receiver.haystack.remove(0);
				System.out.println(tmp_msg);
			}
		}
		receiver.cont = false;
	}

}
