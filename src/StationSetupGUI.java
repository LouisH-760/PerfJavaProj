import java.util.Scanner;

/**
 * GUI for the setup of the station
 * @author Louis Hermier
 *
 */
public class StationSetupGUI{

	public String sensorIp;
	public String stationName;
	private Scanner input;
	
	public StationSetupGUI() {
		input = new Scanner(System.in);
	}
	
	public void init() {
		System.out.println("Sensor IP?");
		sensorIp = input.nextLine();
		if (input.hasNext())
			input.next();
		
		System.out.println("Station Name?");
		stationName = input.nextLine();
	}
	
	public void close() {
		input.close();
	}

}
