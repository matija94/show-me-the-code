package frontend.pascal.parsers;

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
