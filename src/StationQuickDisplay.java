
public class StationQuickDisplay {
	public static void minmax(double min, double max) {
		System.out.println(String.format("Lowest: %.02s, Highest %.02s", min, max));
	}
	
	public static void notEnoughValues() {
		System.out.println("Not enough values (at least two needed) arrived yet.");
	}
}
