import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Scanner;

/**
 * User interaction for the sensor
 * @author LouisHermier, ArnoChaidron
 *
 */
public class SensorGUI implements Runnable{
	private final String TEMP_CUE = "What is the current temperature? (double) (CTRL+C to quit)";
	private final double MIN_TEMP = -20;
	private final double MAX_TEMP = 50;
	
	public volatile double temp;
	public volatile boolean cont;
	
	private Scanner input;
	private String tmp;
	private double tmpDouble;
	
	public SensorGUI() {
		input = new Scanner(System.in);
		cont = true;
	}
	
	@Override
	public void run() {
		while (cont) {
			do {
				NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
				System.out.println(TEMP_CUE);
				tmp = input.next();
				try {
					Number number = format.parse(tmp);
					tmpDouble = number.doubleValue();
				} catch (ParseException e) {
					tmpDouble = MAX_TEMP + 1;
				}
			} while (!(tmpDouble >= MIN_TEMP && tmpDouble <= MAX_TEMP));
			temp = tmpDouble;
		}
		input.close();
	}
}