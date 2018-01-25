package frontend.pascal;

import java.io.IOException;

import frontend.EofToken;
import frontend.Parser;
import frontend.Scanner;
import frontend.Token;
import frontend.TokenType;
import frontend.pascal.parsers.StatementParser;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.SymTabEntry;
import messages.Message;
import messages.MessageType;

/**
 * TOP-DOWN Pascal parser
 *
 */
public class PascalParserTD extends Parser {

	protected static PascalErrorHandler errorHandler = new PascalErrorHandler();
	
	public PascalParserTD(Scanner scanner) {
		super(scanner);
	}
	
	/**
	 * Constructor for subclasses
	 * @param parent the parent parser
	 */
	public PascalParserTD(PascalParserTD parent) {
		super(parent.getScanner());
	}
	
	/**
	* Parse a Pascal source program and generate the symbol table
	* and the intermediate code.
	*/
	/*@Override
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
*/

	
	@Override
	public void parse() throws Exception {
		long startTime = System.currentTimeMillis();
		iCode = ICodeFactory.createICode();
	
		try {
			Token token = nextToken();
			ICodeNode rootNode = null;
		
			// look for the BEGIN token to parse compound statement
			if (token.getType() == PascalTokenType.BEGIN) {
				StatementParser statementParser = new StatementParser(this);
				rootNode = statementParser.parse(token);
				token = currentToken();
			}
			else {
				errorHandler.flag(token, PascalErrorCode.UNEXPECTED_TOKEN, this);
			}
			
			// look for the final period
			if (token.getType() != PascalTokenType.DOT) {
				errorHandler.flag(token, PascalErrorCode.MISSING_PERIOD, this);
			}
			
			token = currentToken();
			
			// set the parse tree root node
			if (rootNode != null) {
				iCode.setRoot(rootNode);
			}
			
			// send the parser summary message
			float elapsedTime = (System.currentTimeMillis() - startTime);
			sendMessage(new Message(MessageType.PARSER_SUMMARY, 
									new Number[] {token.getLineNum(), getErrorCount(), elapsedTime}));
		}
		catch (IOException e) {
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
