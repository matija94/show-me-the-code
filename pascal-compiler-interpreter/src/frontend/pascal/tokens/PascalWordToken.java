package frontend.pascal.tokens;

import frontend.Source;
import frontend.pascal.PascalTokenType;

public class PascalWordToken extends PascalToken {

	public PascalWordToken(Source source) throws Exception {
		super(source);
	}

	@Override
	protected void extract() throws Exception {
		StringBuilder textBuffer = new StringBuilder();
		char currentChar = currentChar();
	
		// get the word characters ( letter or digit )
		// scanner has already figured out the first character is letter
		while(Character.isLetterOrDigit(currentChar)) {
			textBuffer.append(currentChar);
			currentChar = nextChar();
		}
		
		text = textBuffer.toString();
		
		// is it reserved word or an identifier ? 
		type = (PascalTokenType.RESERVED_WORDS.contains(text.toLowerCase()))
				? PascalTokenType.valueOf(text.toUpperCase())
				: PascalTokenType.IDENTIFIER;
	}
	
}
