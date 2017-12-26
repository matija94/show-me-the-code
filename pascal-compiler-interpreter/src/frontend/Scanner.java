package frontend;

/**
 * A language-independent framework class. This abstract class will be implemented by language-specific subclasses
 *
 */
public abstract class Scanner {
	
	protected Source source; // source of the program
	private Token currentToken;

	public Scanner(Source source) {
		this.source = source;
	}
	
	/**
	 * @return the current token
	 */
	public Token currentToken() {
		return currentToken;
	}
	
	/**
	 * Return next token from the source
	 * @return next token from the source
	 * @throws Exception if an error occurred
	 */
	public Token nextToken() throws Exception{
		currentToken = extractToken();
		return currentToken;
	}
	
	/**
	 * Do the actual work of the extracting and returning next token from the source.
	 * Implemented by the scanner subclass
	 * @return the next token
	 * @throws Exception
	 */
	protected abstract Token extractToken() throws Exception;
		
	/**
	 * Call Source currentChar() method
	 * @return the current character
	 * @throws Exception if an error occurred
	 */
	public char currentChar() throws Exception {
		return source.currentChar();
	}
	
	/**
	 * Call Source nextChar() method
	 * @return the next character
	 * @throws Exception if an error occurred
	 */
	public char nextChar() throws Exception { 
		return source.nextChar();
	}
}
