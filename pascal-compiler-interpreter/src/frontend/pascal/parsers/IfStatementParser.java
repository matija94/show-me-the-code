package frontend.pascal.parsers;

import java.util.EnumSet;

import frontend.Token;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeNodeTypeImpl;

public class IfStatementParser extends PascalParserTD {

	public IfStatementParser(PascalParserTD parent) {
		super(parent);
	}

	private static final EnumSet<PascalTokenType> THEN_SET = 
			StatementParser.STMT_START_SET.clone();
	static {
		THEN_SET.add(PascalTokenType.THEN);
		THEN_SET.addAll(StatementParser.STMT_FOLLOW_SET);
	}
	
	public ICodeNode parse(Token token) throws Exception {
		token = nextToken();
		
		ICodeNode ifNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.IF);
		
		ExpressionParser ep = new ExpressionParser(this);
		ifNode.addChild(ep.parse(token));
	
		token = synchronize(THEN_SET);
		if (token.getType() == PascalTokenType.THEN) {
			token = nextToken();
		}
		else {
			errorHandler.flag(token, PascalErrorCode.MISSING_THEN, this);
		}
		
		StatementParser sp = new StatementParser(this);
		ifNode.addChild(sp.parse(token));
	
		token = currentToken();
		if (token.getType() == PascalTokenType.ELSE) { // look for ELSE
			token = nextToken();
			ifNode.addChild(sp.parse(token));
		}
		
		return ifNode;
	}
	
	
}
