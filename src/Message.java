import java.util.Arrays;
import java.util.List;

/**
 * Define a Message for the weatherstation
 * @author Louis Hermier
 *
 */
public class Message {
	// create a list of all types
	public final static List<String> TYPES = Arrays.asList(new String[]{"INFO", "DATA", "STOP"});
	public final static String SEP = ";";
	
	private String id;
	
	// replace by own class?
	private String type;
	
	private String contents;
	
	/**
	 * Get a Message object from it's various parameters
	 * @param type     : Type of the message
	 * @param id       : ID of the sender
	 * @param contents : Content of the message
	 */
	public Message(String type, String id, String contents) {
		setType(type);
		setId(id);
		setContents(contents);
	}
	
	/**
	 * Get a Message object from it's String form
	 * @param message : Output of the message.toString();
	 */
	public Message(String message) {
		String[] split = message.split(SEP);
		Helper.check(split.length == 3, "Wrong message format");
		
		// reconstruct the message from the string
		setType(split[0]);
		setId(split[1]);
		setContents(split[2]);
	}
	
	/**
	 * Set the type of the message
	 * @param type: INFO, ...
	 */
	public void setType(String type) {
		Helper.check(TYPES.contains(type), "Wrong Type");
		this.type = type;
	}
	
	/**
	 * Set the sender ID of the message
	 * @param id: TBD
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Set the contents of the message
	 * @param contents: content string
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	/**
	 * Get the type of the message
	 * @return type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Get the ID of the message
	 * @return ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Get the content of the message
	 * @return contents
	 */
	public String getContents () {
		return contents;
	}
	
	/**
	 * Convert the message to a String (for sending, ...)
	 */
	public String toString() {
		return getType() + SEP + getId() + SEP + getContents();
	}
}
