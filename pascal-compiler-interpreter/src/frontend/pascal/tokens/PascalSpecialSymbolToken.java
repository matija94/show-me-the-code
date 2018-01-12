package frontend.pascal.tokens;

import frontend.Source;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalTokenType;

public class PascalSpecialSymbolToken extends PascalToken {

	public PascalSpecialSymbolToken(Source source) throws Exception {
		super(source);
	}
	
	@Override
	protected void extract() throws Exception {
		char currentChar = currentChar();
		
		text = Character.toString(currentChar);
		type = null;
		
		boolean done = false;
		// check special symbols with length 2 
		switch (currentChar) {

		// is it := ?
		case ':':
			currentChar = nextChar(); // consume colon
			if (currentChar == '=') {
				text += currentChar;
				nextChar(); // consume equal
			}
			
			done = true;
			break;
		// is it <> || <= || < ?
		case '<':
			currentChar = nextChar(); // consume <
			
			if (currentChar == '>' || currentChar == '=') {
				text += currentChar;
				nextChar(); // consume 2nd char
			}
			
			done = true;
			break;
		// is it > || >=
		case '>' :
			currentChar = nextChar(); // consume >
			
			if (currentChar == '=') {
				text += currentChar;
				nextChar();
			}
			
			done = true;
			break;
		// is it ..
		case '.' :
			currentChar = nextChar(); // consume .
			if (currentChar == '.') {
				text += currentChar;
				nextChar();
			}
			
			done = true;
			break;
		default:
			if (PascalTokenType.SPECIAL_SYMBOLS.containsKey(Character.toString(currentChar))) {
				nextChar();
			}
			else {
				nextChar();
				type = PascalTokenType.ERROR;
				value = PascalErrorCode.INVALID_CHARACTER;
			}
			break;
		}
		
		if (type == null) {
			type = PascalTokenType.SPECIAL_SYMBOLS.get(text);
		}
	}
	

}
