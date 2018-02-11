package frontend.pascal.parsers;

import java.util.EnumSet;

import frontend.Token;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.SymTabEntry;
import intermediate.icodeimpl.ICodeKeyImpl;
import intermediate.icodeimpl.ICodeNodeTypeImpl;

public class AssignmentStatementParser extends StatementParser {

	public AssignmentStatementParser(PascalParserTD parent) {
		super(parent);
	}
	
	private static final EnumSet<PascalTokenType> COLON_EQUALS_SET = 
			EnumSet.copyOf(ExpressionParser.EXPR_START_SET);
	static {
		COLON_EQUALS_SET.add(PascalTokenType.COLON_EQUALS);
		COLON_EQUALS_SET.addAll(StatementParser.STMT_FOLLOW_SET);
	}
	

	public ICodeNode parse(Token token) throws Exception {
		ICodeNode assignNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.ASSIGN);
		
		String targetName = token.getText().toLowerCase();
		SymTabEntry targetId = symTabStack.lookup(targetName);
		if (targetId == null) {
			targetId = symTabStack.enterLocal(targetName);
		}
		targetId.appendLineNumber(token.getLineNum());
		
		token = nextToken(); // consume the identifier token
		
		ICodeNode variableNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.VARIABLE);
		variableNode.setAttribute(ICodeKeyImpl.ID, targetId);
		
		assignNode.addChild(variableNode);
		
		// synchronize on the := token
		token = synchronize(COLON_EQUALS_SET);
		if (token.getType() == PascalTokenType.COLON_EQUALS) {
			token = nextToken();
		}
		else {
			errorHandler.flag(token, PascalErrorCode.MISSING_COLON_EQUALS, this);
		}
		
		ExpressionParser ep = new ExpressionParser(this);
		assignNode.addChild(ep.parse(token));
		
		return assignNode;
	}
}
