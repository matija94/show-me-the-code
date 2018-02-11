package frontend.pascal.parsers;

import frontend.Token;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeNodeTypeImpl;

public class RepeatStatementParser extends PascalParserTD {

	public RepeatStatementParser(PascalParserTD parent) {
		super(parent);
	}

	
	public ICodeNode parse(Token token) throws Exception {
		token = nextToken(); // consume the REPEAT
		
		// create loop and test nodes
		ICodeNode loopNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.LOOP);
		ICodeNode testNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.TEST);
		
		StatementParser sp = new StatementParser(this);
		sp.parseList(token, loopNode, PascalTokenType.UNTIL, PascalErrorCode.MISSING_UNTIL);
		
		token = currentToken();
		
		// parse test expression
		ExpressionParser ep = new ExpressionParser(this);
		testNode.addChild(ep.parse(token));
		
		// add test node as last child of loop node
		loopNode.addChild(testNode);
		
		return loopNode;
	}
}
