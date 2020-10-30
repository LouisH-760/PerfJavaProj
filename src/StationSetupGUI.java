import java.util.Scanner;

/**
 * GUI for the setup of the station
 * @author Louis Hermier
 *
 */
public class StationSetupGUI{

	public String sensorIp;
	private Scanner input;
	
	public StationSetupGUI() {
		input = new Scanner(System.in);
	}
	
	public void init() {
		System.out.println("Sensor IP?");
		sensorIp = input.nextLine();
	}
	
	public void close() {
		input.close();
	}

}
