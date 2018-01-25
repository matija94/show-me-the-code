package frontend.pascal.parsers;

import frontend.Token;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeNode;

public class StatementParser extends PascalParserTD {

	public StatementParser(PascalParserTD parent) {
		super(parent);
	}
	
	/**
	 * Parse a statement.
	 * To be overridden by the specialized statement parser subclasses.
	 * @param token
	 * @return
	 */
	public ICodeNode parse(Token token) {
		ICodeNode statementNode = null;
		
		switch ((PascalTokenType) token.getType()) {
		case BEGIN: {
			CompoundStatementParser csp = new CompoundStatementParser(this);
			statementNode = csp.parse(token);
		}
			
			break;

		default:
			break;
		}
	}
}
