package frontend.pascal.parsers;

import frontend.Token;
import frontend.pascal.PascalParserTD;
import intermediate.ICodeNode;

public class ExpressionParser extends StatementParser {

	public ExpressionParser(PascalParserTD parent) {
		super(parent);
	}

	
	@Override
	public ICodeNode parse(Token token) throws Exception {
		return parseExpression(token);
	}
}
