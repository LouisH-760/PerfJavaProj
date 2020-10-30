
public class Main {
	public static void main(String[] args) {
		Thread t;
		try {
			t = new Thread(new StationLogic(25642));
			t.run();
			Thread t1 = new Thread(new SensorLogic("aa", "bb", 25642));
			t1.run();
			t.join();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
