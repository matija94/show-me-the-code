package frontend.pascal;

import frontend.EofToken;
import frontend.Scanner;
import frontend.Source;
import frontend.Token;
import frontend.pascal.tokens.PascalErrorToken;
import frontend.pascal.tokens.PascalNumberToken;
import frontend.pascal.tokens.PascalSpecialSymbolToken;
import frontend.pascal.tokens.PascalStringToken;
import frontend.pascal.tokens.PascalWordToken;

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
		
		skipWhitespace();
		
		Token token;
		char currentChar = currentChar();
		
		if (currentChar == Source.EOF) {
			token = new EofToken(source);
		}
		else if (Character.isLetter(currentChar)) {
			token = new PascalWordToken(source);
		}
		else if (Character.isDigit(currentChar)) {
			token = new PascalNumberToken(source);
		}
		else if (currentChar == '\"') {
			token = new PascalStringToken(source);
		}
		else if (PascalTokenType.SPECIAL_SYMBOLS
					.containsKey(Character.toString(currentChar))) {
			token = new PascalSpecialSymbolToken(source);
		}
		else {
			token = new PascalErrorToken(source, PascalErrorCode.INVALID_CHARACTER, Character.toString(currentChar));
			
			nextChar();
		}
		return token;
	}
	
	private void skipWhitespace() throws Exception { 
		char currentChar = currentChar();
		
		while (Character.isWhitespace(currentChar) || (currentChar == '{')) {
			// start of a comment ? 
			if (currentChar == '{') {
				do {
					currentChar = nextChar(); // consume comment character
				}while ((currentChar != '}') && (currentChar != Source.EOF));

				// found closing  } comment ? 
				if (currentChar == '}') {
					currentChar = nextChar(); // consume end of comment char
				}
			}
			else {
				currentChar = nextChar(); // consume whitespace 
			}
		}
	}
	
}
