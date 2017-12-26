package messages;

public interface MessageListener {

	/**
	 * Called to to receive a message sent by a message producer
	 * @param message the message that was sent
	 */
	public void messageRecieved(Message message);
}
