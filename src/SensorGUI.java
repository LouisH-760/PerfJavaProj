import java.util.Scanner;

/**
 * User interaction for the sensor
 * @author LouisHermier
 *
 */
public class SensorGUI implements Runnable{
	private final String TEMP_CUE = "What is the current temperature? (double)";
	private final double MIN_TEMP = -20;
	private final double MAX_TEMP = 50;
	
	public volatile double temp;
	public volatile boolean cont;
	
	private Scanner input;
	private double tmpDouble;
	
	public SensorGUI() {
		input = new Scanner(System.in);
		cont = true;
	}
	
	@Override
	public void run() {
		while (cont) {
			do {
				System.out.println(TEMP_CUE);
				tmpDouble = input.nextDouble();
			} while (!(tmpDouble >= MIN_TEMP && tmpDouble <= MAX_TEMP));
			temp = tmpDouble;
		}
		input.close();
	}
}
