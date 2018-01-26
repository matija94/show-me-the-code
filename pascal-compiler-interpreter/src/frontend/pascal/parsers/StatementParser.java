package frontend.pascal.parsers;

import frontend.EofToken;
import frontend.Token;
import frontend.TokenType;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeKeyImpl;
import intermediate.icodeimpl.ICodeNodeTypeImpl;

public class StatementParser extends PascalParserTD {

	public StatementParser(PascalParserTD parent) {
		super(parent);
	}
	
	/**
	 * Parse a statement.
	 * To be overridden by the specialized statement parser subclasses.
	 * @param token
	 * @return
	 * @throws Exception 
	 */
	public ICodeNode parse(Token token) throws Exception {
		ICodeNode statementNode = null;
		
		switch ((PascalTokenType) token.getType()) {
		
			case BEGIN: {
				CompoundStatementParser csp = new CompoundStatementParser(this);
				statementNode = csp.parse(token);
				break;
			}
		
			case IDENTIFIER: {
				AssignmentStatementParser asp = new AssignmentStatementParser(this);
				statementNode = asp.parse(token);
				break;
			}
		
			default: {
				statementNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.NO_OP);
				break;
			}
		}
		
		setLineNumber(statementNode, token);
	
		return statementNode;
	}
	
	protected void parseList(Token token, ICodeNode parentNode, PascalTokenType terminator, PascalErrorCode errorCode) throws Exception {
		// loop to parse each token
		// until the END token or the end of source file
		while (!(token instanceof EofToken) && token.getType() != terminator) {
			
			ICodeNode statementNode = parse(token);
			
			parentNode.addChild(statementNode);
			
			token = currentToken();
			TokenType tokenType = token.getType();
			
			if (tokenType == PascalTokenType.SEMICOLON) {
				token = nextToken(); // consume the ;
			}
			
			else if (tokenType == PascalTokenType.IDENTIFIER) {
				errorHandler.flag(token, PascalErrorCode.MISSING_SEMICOLON, this);
			}
			
			else if (tokenType != terminator) {
				errorHandler.flag(token, PascalErrorCode.UNEXPECTED_TOKEN, this);
			}
		}
		
		if (token.getType() == terminator) {
			token = nextToken();
		}
		else {
			errorHandler.flag(token, errorCode, this);
		}
	}

	protected void setLineNumber(ICodeNode node, Token token) {
		if (node != null) {
			node.setAttribute(ICodeKeyImpl.LINE, token.getLineNum());
		}
	}
}
