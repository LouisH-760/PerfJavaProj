import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		Thread t = new Thread(new StationLogic(25642));
		try {
			Thread t1 = new Thread(new SensorLogic("aa", "bb", 25642));
			t1.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t.run();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
