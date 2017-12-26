package frontend.pascal;

import frontend.EofToken;
import frontend.Scanner;
import frontend.Source;
import frontend.Token;

/**
 * Pascal Scanner
 *
 */
public class PascalScanner extends Scanner {

	public PascalScanner(Source source) {
		super(source);
	}

	/**
	* Extract and return the next Pascal token from the source.
	* @return the next token.
	* @throws Exception if an error occurred.
	*/
	@Override
	protected Token extractToken() throws Exception {
		Token token;
		char currentChar = currentChar();
		
		if (currentChar == Source.EOF) {
			token = new EofToken(source);
		}
		
		else {
			token = new Token(source);
		}
		
		return token;
	}
	
	
}
