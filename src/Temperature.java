/**
 * Build temperatures from string and vice-versa
 * @author Louis Hermier
 *
 */
public class Temperature {
	private String timestamp;
	private double temp;
	
	public Temperature(String timestamp, double temp) {
		setTimestamp(timestamp);
		setTemp(temp);
	}
	
	public Temperature(String temperature) {
		String[] expl = temperature.split(Message.WEAK_SEP);
		setTimestamp(expl[0]);
		setTemp(Double.parseDouble(expl[1]));
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setTemp(double temp) {
		this.temp = temp;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public double getTemp() {
		return temp;
	}
	
	public String toString() {
		return getTimestamp() + Message.WEAK_SEP + getTemp();
	}
}
