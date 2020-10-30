/**
 * Message class for received messages. Adds storage of the IP of the sender.
 * @author louis
 *
 */
public class ReceivedMessage extends Message{

	private String address;
	
	public ReceivedMessage(String message) {
		super(message);
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return address;
	}
	
	public Message toMessage() {
		return new Message(getType(), getId(), getContents());
	}
	
}
