package frontend.pascal.tokens;

import frontend.Source;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalTokenType;

public class PascalErrorToken extends PascalToken {

	/**
	 * Constructor.
	 * 
	 * @param source
	 *            the source from where to fetch subsequent characters.
	 * @param errorCode
	 *            the error code.
	 * @param tokenText
	 *            the text of the erroneous token.
	 * @throws Exception
	 *             if an error occurred.
	 */
	public PascalErrorToken(Source source, PascalErrorCode errorCode, String tokenText) throws Exception {
		super(source);
		this.text = tokenText;
		this.type = PascalTokenType.ERROR;
		this.value = errorCode;
	}

	/**
	 * Do nothing. Do not consume any source characters.
	 * 
	 * @throws Exception
	 *             if an error occurred.
	 */
	protected void extract() throws Exception {
	}
}
