package frontend.pascal;

import frontend.Parser;
import frontend.Token;
import messages.Message;
import messages.MessageType;

/**
 * Error handle for pascal syntax errors
 */
public class PascalErrorHandler {
	
	private static final int MAX_ERRORS = 25;
	
	private static int errorCount = 0;
	
	/**
	* Flag an error in the source line.
	* @param token the bad token.
	* @param errorCode the error code.
	* @param parser the parser.
	* @return the flagger string.
	*/
	public void flag(Token token, PascalErrorCode errorCode, Parser parser) {
		
		// notify the parser's listeners
		parser.sendMessage(new Message(MessageType.SYNTAX_ERROR, 
										new Object[] {token.getLineNum(),
													  token.getPosition(),
													  token.getText(),
													  errorCode.toString()}));
	
		if (++errorCount > MAX_ERRORS) {
			abortTranslation(PascalErrorCode.TOO_MANY_ERRORS, parser);
		}
	}

	/**
	* Abort the translation.
	* @param errorCode the error code.
	* @param parser the parser.
	*/
	public void abortTranslation(PascalErrorCode errorCode, Parser parser) {
		String fatalText = "FATAL ERROR: " + errorCode.toString();
		parser.sendMessage(new Message(MessageType.SYNTAX_ERROR, 
										new Object[] {0, 0, "", fatalText}));
		
		System.exit(errorCode.getStatus());
	}

	public int getErrorCount() {
		return errorCount;
	}
	
}
