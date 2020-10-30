import java.io.IOException;
import java.util.Iterator;

public class StationLogic implements Runnable{
	
	private TCPReceiver receiver;
	private Thread t_receiver;
	private ReceivedMessage tmp_msg;
	
	private Message draft;
	private Thread t_sender;
	
	private StationGUI gui;
	private Thread t_gui;
	
	public volatile boolean cont;
	public volatile Actions action;
	
	public StationLogic() throws IOException {
		receiver = new TCPReceiver(TCPCommon.STATION_PORT);
		t_receiver = new Thread(receiver);
		cont = true;
		action = Actions.INIT;
		
		gui = new StationGUI();
		t_gui = new Thread(gui);
	}
	
	@Override
	public void run() {
		t_receiver.start();
		t_gui.start();
		while(action == Actions.INIT)
			// wait
		while(action != Actions.QUIT) {
			if(action != Actions.PASS) {
				
			}
		}
		gui.cont = false;
		receiver.cont = false;
	}

}
