package frontend;

import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalScanner;

/**
 * Factory class that creates parsers for specific source languages
 *
 */
public class FrontendFactory {

	/**
	 * Create a parser.
	 * @param language language source was written with
	 * @param type type of the parser for specific language
	 * @param Source the source object
	 * @return parser implementation for specific language
	 * @throws Exception if an error occurred
	 */
	public static Parser createPraser(String language, String type, Source Source) throws Exception {
		if (language.equalsIgnoreCase("pascal") && type.equalsIgnoreCase("top-down")) {
			return new PascalParserTD(new PascalScanner(Source));
		}
		else {
			throw new Exception("Not supported");
		}
	}
}
