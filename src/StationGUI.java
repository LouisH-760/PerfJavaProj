import java.util.Scanner;

/**
 * User interface for the station.
 * @author Louis Hermier
 *
 */
public class StationGUI implements Runnable{
	private String name;
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
			parent.action = choiceGui();
		}
	}
	
	public Actions choiceGui() {
		System.out.println("1. Start Communication with the sensor");
		System.out.println("2. Stop communication with the sensor");
		System.out.println("3. Min/Max");
		System.out.println("4. Reset");
		System.out.println("99. Quit");
		System.out.println("??. Pass");
		System.out.print("int choice >");
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
		case 99:
			return Actions.QUIT;
		default:
			return Actions.PASS;	
		}
	}
}
