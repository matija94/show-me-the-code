package frontend;

import java.io.BufferedReader;
import java.io.IOException;

import messages.Message;
import messages.MessageHandler;
import messages.MessageListener;
import messages.MessageProducer;

import messages.MessageType;

/**
 * The framework class that represents the source program.
 *
 */
public class Source implements MessageProducer {

	public static final char EOL = '\n';
	public static final char EOF = (char) 0;
	
	private BufferedReader reader; // reader for the source program
	private String line; // source line
	private int lineNum; // current source line number
	private int currentPos; // current source line position
	
	private static MessageHandler messageHandler = new MessageHandler();
	
	public Source(BufferedReader reader) throws Exception{
		this.lineNum = 0;
		this.currentPos = -2;
		this.reader = reader;
	}
	
	/**
	 * Return the source character at the current position.
	 * @return the source character at the current position
	 * @throws Exception if an error occurred
	 */
	public char currentChar() throws Exception {
		// first time ?
		if (getCurrentPos() == -2) {
			readLine();
			return nextChar();
		}
		
		// end of file ? 
		else if (line == null) {
			return EOF;
		}
		
		// end of line ?
		else if ((getCurrentPos() == -1) || (getCurrentPos() == line.length())) {
			return EOL;
		}
		
		// need to read next line ?
		else if (getCurrentPos() > line.length()) {
			readLine();
			return nextChar();
		}
		
		// return char at the current pos 
		else {
			return line.charAt(getCurrentPos());
		}
	}
	
	/**
	 * Consume the current source character and return the next character
	 * @return the next character
	 * @throws Exception if an error occurred
	 */
	public char nextChar() throws Exception {
		this.currentPos += 1;
		return currentChar();
	}
	
	/**
	 * Return the source character following the current character without consuming current character
	 * @return the following character
	 * @throws Exception if an error occurred
	 */
	public char peekChar() throws Exception {
		currentChar();
		if (line == null) {
			return EOF;
		}
		
		int nextPos = getCurrentPos() + 1;
		return nextPos < line.length() ? line.charAt(nextPos) : EOL;
	}
	
	/**
	 * Read the next source line
	 * @throws IOException if an I/O error occurred
	 */
	public void readLine() throws IOException {
		line = reader.readLine(); // null when at the end of the source
		this.currentPos = -1;
		
		if (line != null) {
			lineNum += 1;
			// send a source line message containing line number and source line to all listeners
			sendMessage(new Message(MessageType.SOURCE_LINE, new Object[] {lineNum, line}));
		}
		
	}
	
	/**
	 * Close the source
	 * @throws IOException if an I/O error occurred.
	 */
	public void close() throws IOException {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public int getLineNum() {
		return lineNum;
	}

	public int getCurrentPos() {
		return currentPos;
	}

	/**
	 * Add Source message listener
	 */
	@Override
	public void addMessageListener(MessageListener listener) {
		messageHandler.addListener(listener);
	}

	/**
	 * Remove Source message listener
	 */
	@Override
	public void removeMessageListener(MessageListener listener) {
		messageHandler.removeListener(listener);
	}

	/**
	 * Notify listeners after message is set
	 */
	@Override
	public void sendMessage(Message message) {
		messageHandler.sendMessage(message);
	}

}
