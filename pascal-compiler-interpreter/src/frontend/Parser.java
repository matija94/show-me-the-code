package frontend;
import intermediate.ICode;
import intermediate.SymTabFactory;
import intermediate.SymTabStack;
import messages.Message;
import messages.MessageHandler;
import messages.MessageListener;
import messages.MessageProducer;

/**
 * 
 * A language independent framework class. This abstract parser class will be implemented by the language specific subclasses
 */
public abstract class Parser implements MessageProducer {

	protected static SymTabStack symTabStack; // symbol table stack
	protected static MessageHandler messageHandler;
	
	static {
		symTabStack = SymTabFactory.createSymTabStack();
		messageHandler = new MessageHandler();
	}
	
	private Scanner scanner; // scanner used with this parser
	protected ICode iCode; // intermediate code generated by this parser

	protected Parser(Scanner scanner) {
		this.scanner = scanner;
	}
	
	/**
	 * Parse a source program and generate the intermediate code and the 
	 * symbol table. To be implemented by the language-specific parser subclass.
	 * @throws Exception if an error occurred,
	 */
	public abstract void parse() throws Exception;
	
	/**
	 * Return the number of syntax errors found by the parser.
	 * To be implemented by the language-specific parser subclass.
	 * @return the error count.
	 */
	public abstract int getErrorCount();
	
	/**
	 * Call the Scanner currentToken() method
	 * @return current token
	 */
	public Token currentToken() {
		return getScanner().currentToken();
	}
	
	/**
	 * Call the Scanner nextToken() method
	 * @return next token 
	 * @throws Exception if an error occurred.
	 */
	public Token nextToken() throws Exception{
		return this.scanner.nextToken();
	}
	
	/**
	 * Add parser message listener
	 */
	@Override
	public void addMessageListener(MessageListener listener) {
		messageHandler.addListener(listener);
	}
	
	/**
	 * Remove parser message listener
	 */
	@Override
	public void removeMessageListener(MessageListener listener) {
		messageHandler.removeListener(listener);
	}
	
	/**
	 * Notify the listeners after message is set
	 */
	public void sendMessage(Message message) {
		messageHandler.sendMessage(message);
	};
	
	public SymTabStack getSymTabStack() {
		return symTabStack;
	}
	
	public ICode getICode() {
		return iCode;
	}

	public Scanner getScanner() {
		return scanner;
	}
	
}
