/**
 * QoL functions without real scope
 * @author Louis Hermier
 *
 */
public class Helper {
	/**
	 * Throw an exception if a condition is not met
	 * @param condition : condition to check upon
	 * @param message   : message in case of exception
	 */
	public static void check(boolean condition, String message) {
		if(!condition)
			throw new IllegalArgumentException(message);
	}
	
	public static int str2int(String str) {
		try {
			int tmpint = Integer.parseInt(str);
			return tmpint;
		} catch (Exception e) {
			return -1;
		}
	}
}
