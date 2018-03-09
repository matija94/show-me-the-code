package frontend.pascal.parsers;

import java.util.EnumSet;

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
	
	// synchronization set for starting a statement
	protected static final EnumSet<PascalTokenType> STMT_START_SET = 
			EnumSet.of(PascalTokenType.BEGIN, PascalTokenType.CASE, PascalTokenType.IF, PascalTokenType.REPEAT, PascalTokenType.WHILE,PascalTokenType.FOR,PascalTokenType.IDENTIFIER, PascalTokenType.SEMICOLON);
	
	// synchronization set for following a statement
	protected static final EnumSet<PascalTokenType> STMT_FOLLOW_SET = 
			EnumSet.of(PascalTokenType.SEMICOLON, PascalTokenType.END, PascalTokenType.ELSE, PascalTokenType.UNTIL, PascalTokenType.DOT);
	
	
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

			case REPEAT: {
				RepeatStatementParser rsp = new RepeatStatementParser(this);
				statementNode = rsp.parse(token);
				break;
			}

			case FOR: {
				ForStatementParser fsp = new ForStatementParser(this);
				statementNode = fsp.parse(token);
				break;
			}
			
			case WHILE: {
				WhileStatementParser wsp = new WhileStatementParser(this);
				statementNode = wsp.parse(token);
				break;
			}
			
			
			case IF: {
				IfStatementParser isp = new IfStatementParser(this);
				statementNode = isp.parse(token);
				break;
			}
			
			/*
			case CASE: {
				CaseStatementParser csp = new CaseStatementParser(this);
				statementNode = csp.parse(token);
				break;
			}*/
			
			default: {
				statementNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.NO_OP);
				break;
			}
		}
		
		setLineNumber(statementNode, token);
	
		return statementNode;
	}
	
	protected void parseList(Token token, ICodeNode parentNode, PascalTokenType terminator, PascalErrorCode errorCode) throws Exception {
		
		// synchronization set for either terminating token or starting token
		EnumSet<PascalTokenType> start_terminatorSet = EnumSet.copyOf(STMT_START_SET);
		start_terminatorSet.add(terminator);
		
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
			
			else if (STMT_START_SET.contains(tokenType)) {
				errorHandler.flag(token, PascalErrorCode.MISSING_SEMICOLON, this);
			}
			
			// synchronize at the start of the next statement or at the terminator
			token = synchronize(start_terminatorSet);
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
