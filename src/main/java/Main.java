import java.io.IOException;
import java.util.Scanner;

public class Main {
	private static String vendorID = "MyCoolVendor";
	private static String productID = "MyCoolSensor 2";
	private static String location = "MyCoolLocation";
	
	/**
	 * Entry point for the JAR, prompts the user wether to start as sensor or station, 
	 * then start and join the corresponding thread
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException{
		Thread t;
		Scanner input = new Scanner(System.in);
		System.out.println("Type in 's' to start this as a station, any other input will start as a sensor.");
		System.out.println("If starting as a sensor, input at least one value before requesting data from the station to avoid default values");
		System.out.print(">");
		String tmp = input.nextLine();
		if(tmp.equals("s")) {
			System.out.println("Starting as a station");
			t = new Thread(new StationLogic());
		} else {
			System.out.println("Starting as a sensor");
			t = new Thread(new SensorLogic(vendorID, productID, location));
		}
		t.start();
		t.join();
		input.close();
	}
}
