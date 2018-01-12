package frontend.pascal;

import frontend.EofToken;
import frontend.Parser;
import frontend.Token;
import frontend.TokenType;
import intermediate.SymTabEntry;
import messages.Message;
import messages.MessageType;

/**
 * TOP-DOWN Pascal parser
 *
 */
public class PascalParserTD extends Parser {

	protected static PascalErrorHandler errorHandler = new PascalErrorHandler();
	
	public PascalParserTD(PascalParserTD parent) {
		super(parent.getScanner());
	}
	
	/**
	* Parse a Pascal source program and generate the symbol table
	* and the intermediate code.
	*/
	@Override
	public void parse() throws Exception {
		Token token;
		long startTime = System.currentTimeMillis();
		
		try {
			while (!((token=nextToken()) instanceof EofToken)) {
				TokenType type = token.getType();
				
				if (type == PascalTokenType.IDENTIFIER) {
					String name = token.getText().toLowerCase();
					
					SymTabEntry entry = symTabStack.lookup(name);
					if (entry == null) {
						entry = symTabStack.enterLocal(name);
					}
					
					entry.appendLineNumber(token.getLineNum());
				}
				else if (type == PascalTokenType.ERROR) {
					errorHandler.flag(token, (PascalErrorCode) token.getValue(), 
							this);
				}
			}
			
			// send the parser summary message
			float elapsedTime = (System.currentTimeMillis() - startTime)/1000.0f;
			sendMessage(new Message(MessageType.PARSER_SUMMARY, 
					new Number[] {token.getLineNum(),
								getErrorCount(),
								elapsedTime}));
		}
		catch (Exception e) {
			errorHandler.abortTranslation(PascalErrorCode.IO_ERROR, this);
		}
	}

	/**
	* Return the number of syntax errors found by the parser.
	* @return the error count.
	*/
	@Override
	public int getErrorCount() {
		// TODO Auto-generated method stub
		return errorHandler.getErrorCount();
	}

}
