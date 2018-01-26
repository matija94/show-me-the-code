package frontend.pascal.parsers;

import frontend.Token;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeNodeTypeImpl;

public class CompoundStatementParser extends StatementParser {

	public CompoundStatementParser(PascalParserTD parser) {
		super(parser);
	}
	
	/**
	 * Parse compound statement
	 */
	public ICodeNode parse(Token token) throws Exception {
		
		token = nextToken(); // consume BEGIN
		
		ICodeNode compoundNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.COMPOUND);
		
		StatementParser sp = new StatementParser(this);
		sp.parseList(token, compoundNode, PascalTokenType.END, PascalErrorCode.MISSING_END);
	
		return compoundNode;
	}
}
