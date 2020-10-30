import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class StationLogic implements Runnable{
	
	private String sensorIp;
	public String stationName;
	
	private TCPReceiver receiver;
	private Thread t_receiver;
	
	private Message draft;
	private Thread t_sender;
	
	private StationGUI gui;
	private Thread t_gui;
	
	private StationSetupGUI sGui;
	
	public Message lastNonTemp;
	public Temperature lastTemp;
	
	public volatile boolean cont;
	public volatile Actions action;
	
	private ArrayList<Temperature> temps;
	
	public StationLogic() throws IOException {
		receiver = new TCPReceiver(TCPCommon.STATION_PORT);
		t_receiver = new Thread(receiver);
		cont = true;
		action = Actions.PASS;
		
		gui = new StationGUI(this);
		t_gui = new Thread(gui);
		
		sGui = new StationSetupGUI();
		
		temps = new ArrayList<Temperature>();
	}
	
	@Override
	public void run() {
		t_receiver.start();
		// ask user for the sensor ip
		sGui.init();
		// get the sensor ip
		sensorIp = sGui.sensorIp;
		stationName = sGui.stationName;
		sGui.close();
		// the Setup GUI can be garbage collected
		sGui = null;
		
		t_gui.start();
		while(action != Actions.QUIT) {
			if(!receiver.haystack.isEmpty()) {
				Iterator<ReceivedMessage> iter = receiver.haystack.iterator();
				while(iter.hasNext()) {
					temps.add(new Temperature(iter.next().getContents()));
					iter.remove();
				}
			}
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
			case INFO:
				info();
				break;
			}
		}
		gui.cont = false;
		receiver.cont = false;
	}
	
	private void init() {
		draft = buildMessage(Message.TYPE_DATA, Message.EMPTY);
		t_sender = new Thread(new TCPThrowawaySender(sensorIp, TCPCommon.SENSOR_PORT, draft));
		t_sender.start();
	}
	
	private void stop() {
		draft = buildMessage(Message.TYPE_STOP, Message.EMPTY);
		t_sender = new Thread(new TCPThrowawaySender(sensorIp, TCPCommon.SENSOR_PORT, draft));
		t_sender.start();
	}
	
	private void reset() {
		temps = new ArrayList<Temperature>();
	}
	
	private Message buildMessage(String type, String contents) {
		return new Message(type, stationName, contents);
	}
	
}
