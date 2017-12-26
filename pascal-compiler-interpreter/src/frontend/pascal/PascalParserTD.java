package frontend.pascal;

import frontend.EofToken;
import frontend.Parser;
import frontend.Scanner;
import frontend.Token;
import messages.Message;
import messages.MessageType;

/**
 * TOP-DOWN Pascal parser
 *
 */
public class PascalParserTD extends Parser{

	public PascalParserTD(Scanner scanner) {
		super(scanner);
	}
	
	/**
	* Parse a Pascal source program and generate the symbol table
	* and the intermediate code.
	*/
	@Override
	public void parse() throws Exception {
		Token token;
		long startTime = System.currentTimeMillis();
		
		while (!((token=nextToken()) instanceof EofToken)) {}
		
		// send the parser summary message
		float elapsedTime = (System.currentTimeMillis() - startTime)/1000.0f;
		sendMessage(new Message(MessageType.PARSER_SUMMARY, 
				new Number[] {token.getLineNum(),
							getErrorCount(),
							elapsedTime}));
	}

	/**
	* Return the number of syntax errors found by the parser.
	* @return the error count.
	*/
	@Override
	public int getErrorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
