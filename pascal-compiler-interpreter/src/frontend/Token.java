package frontend;

/**
 * The framework class that represents a token returned by  the scanner.
 */
public class Token {

	
	protected TokenType type; // language-specific token type
	protected String text; // token text
	protected Object value; // token value
	protected Source source; // source 
	private int lineNum; // line number of the token's source line
	protected int position; // position of the first token character

	public Token(Source source) throws Exception {
		this.source = source;
		this.setLineNum(source.getLineNum());
		this.position = source.getCurrentPos();
		
		extract();
	}
	
	/**
	 * Default method to extract only one-character tokens from the source.
	 * Subclasses can override this method to construct language-specific
	 * tokens. After extracting the token, the current source line position
	 * will be one beyond the last token character.
	 * @throws Exception
	 */
	protected void extract() throws Exception { 
		text = Character.toString(currentChar());
		value = null;
		
		nextChar(); // consume currentChar
	}
	
	/**
	 * Call Source currentChar() method
	 * @return the current character from the source
	 * @throws Exception if an error occurred
	 */
	protected char currentChar() throws Exception {
		return source.currentChar();
	}
	
	/**
	 * Call Source nextChar() method
	 * @return the next character from the source
	 * @throws Exception if an error occurred
	 */
	protected char nextChar() throws Exception {
		return source.nextChar();
	}
	
	/**
	 * Call Source peekChar() method
	 * @return the next character from the source without consuming current
	 * @throws Exception
	 */
	protected char peekChar() throws Exception { 
		return source.peekChar();
	}

	public int getLineNum() {
		return lineNum;
	}
}
