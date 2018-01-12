package frontend.pascal.tokens;

import frontend.Source;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalTokenType;

public class PascalStringToken extends PascalToken {

	public PascalStringToken(Source source) throws Exception {
		super(source);
	}
	
	@Override
	protected void extract() throws Exception {
		StringBuilder textBuffer = new StringBuilder();
		StringBuilder valueBuffer = new StringBuilder();
		
		char currentChar = nextChar(); // consume initial quote
		textBuffer.append('\"');
		
		// get string characters
		do {
			// replace any whitespace with a blank
			if (Character.isWhitespace(currentChar)) {
				currentChar = ' ';
			}
			
			// anything except single quote and end of file
			if ( (currentChar != '\"') && (currentChar != Source.EOF)) {
				textBuffer.append(currentChar);
				valueBuffer.append(currentChar);
				currentChar = nextChar(); // consume
			}
			
			// quote ? each pair of adjacent quotes represents a single-quote.
			if (currentChar == '\"') {
				while ((currentChar == '\"') && (peekChar() == '\"')) {
					textBuffer.append("'");
					valueBuffer.append(currentChar);
					// consume two characters
					nextChar();
					currentChar = nextChar();
				}
			}
		} while ((currentChar != '\"') && (currentChar != Source.EOF));
		
		if (currentChar == '\"') {
			nextChar(); // consume final quote
			textBuffer.append('\"');
			
			type = PascalTokenType.STRING;
			value = valueBuffer.toString();
		}
		else {
			type = PascalTokenType.ERROR;
			value = PascalErrorCode.UNEXPECTED_EOF;
		}
		
		text = textBuffer.toString();
		
	}

}
