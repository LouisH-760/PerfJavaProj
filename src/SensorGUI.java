import java.util.Scanner;

/**
 * User interaction for the sensor
 * @author LouisHermier
 *
 */
public class SensorGUI {
	private final String TEMP_CUE = "What is the current temperature? (double)";
	private final double MIN_TEMP = -10;
	private final double MAX_TEMP = 50;
	
	private Scanner input;
	
	public SensorGUI() {
		input = new Scanner(System.in);
	}
	
	public double getTemperature() {
		return 0;
	}
}
