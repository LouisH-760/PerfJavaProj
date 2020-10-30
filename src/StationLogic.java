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
		receiver.cont = false;
	}

}
