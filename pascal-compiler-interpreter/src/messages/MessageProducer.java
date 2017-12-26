package messages;

public interface MessageProducer {

	
	/**
	 * Adds the listener to the listener list
	 * @param listener Message listener
	 */
	public void addMessageListener(MessageListener listener);

	/**
	 * Removes the message listener from the listener list.
	 * @param listener Message listener
	 */
	public void removeMessageListener(MessageListener listener);

	/**
	 * Notify listeners after setting the message
	 * @param message the message to set
	 */
	public void sendMessage(Message message)
}
