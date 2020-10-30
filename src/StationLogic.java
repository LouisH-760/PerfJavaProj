
public class StationLogic implements Runnable{

	private int port;
	
	private TCPReceiver receiver;
	private Thread t_receiver;
	private ReceivedMessage tmp_msg;
	
	private Message draft;
	private Thread t_sender;
	
	public StationLogic(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
