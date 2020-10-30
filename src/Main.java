import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException{
		Thread t;
		int port = 10000;
		Scanner input = new Scanner(System.in);
		System.out.println("a for station");
		String tmp = input.nextLine();
		if(tmp.equals("a")) {
			t = new Thread(new StationLogic());
		} else {
			t = new Thread(new SensorLogic("aa", "bb"));
		}
		t.start();
		t.join();
	}
}
