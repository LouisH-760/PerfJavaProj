import java.util.Scanner;

/**
 * User interface for the station.
 * @author Louis Hermier
 *
 */
public class StationGUI implements Runnable{
	private Scanner input;
	private StationLogic parent;
	
	public StationGUI(StationLogic parent) {
		input = new Scanner(System.in);
		this.parent = parent;
	}
	
	public volatile boolean cont = true;
	
	@Override
	public void run() {
		while(cont) {
			header();
			parent.action = choiceGui();
		}
	}
	
	private Actions choiceGui() {
		// clean input
		System.out.println("1. Start Communication with the sensor");
		System.out.println("2. Stop communication with the sensor");
		System.out.println("3. Min/Max");
		System.out.println("4. Reset");
		System.out.println("5. Sensor info");
		System.out.println("99. Quit");
		System.out.println("??. Pass");
		System.out.print("int choice >");
		while (!input.hasNextInt()) 
			input.next();
		int choice = input.nextInt();
		switch (choice) {
		case 1:
			return Actions.INIT;
		case 2: 
			return Actions.STOP;
		case 3:
			return Actions.MINMAX;
		case 4:
			return Actions.RESET;
		case 5:
			return Actions.INFO;
		case 99:
			return Actions.QUIT;
		default:
			return Actions.PASS;	
		}
	}
	
	private void header() {
		System.out.println("Station: " + parent.stationName);
		try {
			System.out.println("Last non-temperature packet content: " + parent.lastNonTemp);
			System.out.println("Last temperature: " + parent.lastTemp);
		} catch (Exception e) {
			System.out.println("No data available yet.");
		}
		
	}
}
