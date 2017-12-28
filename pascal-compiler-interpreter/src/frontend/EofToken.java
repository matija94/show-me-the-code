package frontend;

import frontend.pascal.PascalTokenType;

/**
 * The generic end of file token
 *
 */
public class EofToken extends Token{

	public EofToken(Source source) throws Exception {
		super(source);
		type = PascalTokenType.END_OF_FILE;
	}
	
	/**
	 * Do nothing. Do not consume any source characters.
	 * @throws Exception if an error occurred
	 */
	@Override
	protected void extract() throws Exception {
		// do nothing
	}
}
