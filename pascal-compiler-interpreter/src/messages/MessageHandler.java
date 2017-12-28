package messages;

import java.util.ArrayList;

public class MessageHandler {

	private Message message;
	private ArrayList<MessageListener> listeners;
	
	public MessageHandler() {
		this.listeners = new ArrayList<>();
	}
	
	/**
	 * Adds the listener to the listeners list
	 * @param listener message listener
	 */
	public void addListener(MessageListener listener) {
		listeners.add(listener);
	}

	/**
	 * Removes the listener from the listener list
	 * @param listener message listener
	 */
	public void removeListener(MessageListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notifies all listeners from the list with the passed message
	 * @param message
	 */
	public void sendMessage(Message message) {
		this.message = message;
		notifyListeners();
	}
	
	private void notifyListeners() {
		listeners.forEach(listener -> listener.messageReceived(message));
	}

}
