import java.io.IOException;
import java.util.Iterator;

public class StationLogic implements Runnable{
	
	private String sensorIp;
	
	private TCPReceiver receiver;
	private Thread t_receiver;
	private ReceivedMessage tmp_msg;
	
	private Message draft;
	private Thread t_sender;
	
	private StationGUI gui;
	private Thread t_gui;
	
	private StationSetupGUI sGui;
	
	public volatile boolean cont;
	public volatile Actions action;
	
	public StationLogic() throws IOException {
		receiver = new TCPReceiver(TCPCommon.STATION_PORT);
		t_receiver = new Thread(receiver);
		cont = true;
		action = Actions.PASS;
		
		gui = new StationGUI(this);
		t_gui = new Thread(gui);
		
		sGui = new StationSetupGUI();
	}
	
	@Override
	public void run() {
		t_receiver.start();
		// ask user for the sensor ip
		sGui.init();
		// get the sensor ip
		sensorIp = sGui.sensorIp;
		sGui.close();
		// the Setup GUI can be garbage collected
		sGui = null;
		
		t_gui.start();
		while(action != Actions.QUIT) {
			switch(action) {
			case PASS:
				break;
			case INIT:
				init();
				break;
			case STOP:
				stop();
				break;
			case RESET:
				reset();
				break;
			case MINMAX:
				minmax();
				break;
			}
		}
		gui.cont = false;
		receiver.cont = false;
	}
	
	
}
