package frontend.pascal.parsers;

import java.util.EnumSet;

import frontend.Token;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeNodeTypeImpl;

public class WhileStatementParser extends PascalParserTD {

	public WhileStatementParser(PascalParserTD parent) {
		super(parent);
	}

	// synchronization set for DO.
	private static final EnumSet<PascalTokenType> DO_SET = 
			EnumSet.copyOf(StatementParser.STMT_START_SET);
	static {
		DO_SET.add(PascalTokenType.DO);
		DO_SET.addAll(StatementParser.STMT_FOLLOW_SET);
	}
	
	
	public ICodeNode parse(Token token) throws Exception {
		token = nextToken(); // consume WHILE
		
		// create LOOP, TEST and NOT nodes
		ICodeNode loopNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.LOOP);
		ICodeNode testNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.TEST);
		ICodeNode notNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.NOT);
		
		// set first child of loop node -- test node
		loopNode.addChild(testNode);
		
		testNode.addChild(notNode);
		
		ExpressionParser ep = new ExpressionParser(this);
		notNode.addChild(ep.parse(token));
		
		// synchronize at DO
		token = synchronize(DO_SET);
		if (token.getType() == PascalTokenType.DO) {
			token = nextToken();
		}
		else {
			errorHandler.flag(token, PascalErrorCode.MISSING_DO, this);
		}
		
		StatementParser sp = new StatementParser(this);
		// parse loop node nested statement and set it as it's second child
		loopNode.addChild(sp.parse(token));
		
		return loopNode;
		
	}
}
