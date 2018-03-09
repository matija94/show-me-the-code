package frontend.pascal.parsers;

import java.util.EnumSet;
import java.util.HashSet;

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

public class CaseStatementParser extends PascalParserTD {

	public CaseStatementParser(PascalParserTD parent) {
		super(parent);
	}

	private static final EnumSet<PascalTokenType> CONSTANT_START_SET = EnumSet.of(PascalTokenType.IDENTIFIER,
			PascalTokenType.INTEGER, PascalTokenType.PLUS, PascalTokenType.MINUS, PascalTokenType.STRING);

	private static final EnumSet<PascalTokenType> OF_SET = CONSTANT_START_SET.clone();
	static {
		OF_SET.add(PascalTokenType.OF);
		OF_SET.addAll(StatementParser.STMT_FOLLOW_SET);
	}

	public ICodeNode parse(Token token) throws Exception {
		token = nextToken();

		ICodeNode selectNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.SELECT);

		ExpressionParser ep = new ExpressionParser(this);
		selectNode.addChild(ep.parse(token));

		token = synchronize(OF_SET);
		if (token.getType() == PascalTokenType.OF) {
			token = nextToken();
		} else {
			errorHandler.flag(token, PascalErrorCode.MISSING_OF, this);
		}

		HashSet<Object> constantSet = new HashSet<>();

		while (!(token instanceof EofToken) && (token.getType() != PascalTokenType.END)) {
			selectNode.addChild(parseBranch(token, constantSet));

			token = currentToken();
			if (token.getType() == PascalTokenType.SEMICOLON) {
				token = nextToken();
			} else if (CONSTANT_START_SET.contains(token.getType())) {
				errorHandler.flag(token, PascalErrorCode.MISSING_SEMICOLON, this);
			}
		}

		if (token.getType() == PascalTokenType.END) {
			token = nextToken();
		} else {
			errorHandler.flag(token, PascalErrorCode.MISSING_END, this);
		}

		return selectNode;
	}

	private ICodeNode parseBranch(Token token, HashSet<Object> constantSet) throws Exception {
		ICodeNode branchNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.SELECT_BRANCH);
		ICodeNode constantsNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.SELECT_CONSTANTS);
		branchNode.addChild(constantsNode);

		parseConstantList(token, constantsNode, constantSet);

		token = currentToken();
		if (token.getType() == PascalTokenType.COLON) {
			token = nextToken();
		} else {
			errorHandler.flag(token, PascalErrorCode.MISSING_COLON, this);
		}

		StatementParser sp = new StatementParser(this);
		branchNode.addChild(sp.parse(token));

		return branchNode;
	}

	// Synchronization set for COMMA.
	private static final EnumSet<PascalTokenType> COMMA_SET = CONSTANT_START_SET.clone();
	static {
		COMMA_SET.add(PascalTokenType.COMMA);
		COMMA_SET.add(PascalTokenType.COLON);
		COMMA_SET.addAll(StatementParser.STMT_START_SET);
		COMMA_SET.addAll(StatementParser.STMT_FOLLOW_SET);
	}

	private void parseConstantList(Token token, ICodeNode constantsNode, HashSet<Object> constantSet) throws Exception {

		while (CONSTANT_START_SET.contains(token.getType())) {
			constantsNode.addChild(parseConstant(token, constantSet));

			token = synchronize(COMMA_SET);

			if (token.getType() == PascalTokenType.COMMA) {
				token = nextToken();
			} else if (CONSTANT_START_SET.contains(token.getType())) {
				errorHandler.flag(token, PascalErrorCode.MISSING_COMMA, this);
			}
		}
	}

	private ICodeNode parseConstant(Token token, HashSet<Object> constantSet) throws Exception {
		TokenType sign = null;
		ICodeNode constantNode = null;

		token = synchronize(CONSTANT_START_SET);
		TokenType tokenType = token.getType();

		if ((tokenType == PascalTokenType.PLUS) || (tokenType == PascalTokenType.MINUS)) {
			sign = tokenType;
			token = nextToken(); // consume sign
		}

		switch ((PascalTokenType) token.getType()) {
		case IDENTIFIER: {
			constantNode = parseIdentifierConstant(token, sign);
			break;
		}
		case INTEGER: {
			constantNode = parseIntegerConstant(token.getText(), sign);
			break;
		}
		case STRING: {
			constantNode = parseCharacterConstant(token, (String) token.getValue(), sign);
			break;
		}
		default: {
			errorHandler.flag(token, PascalErrorCode.INVALID_CONSTANT, this);
			break;
		}
		}

		if (constantNode != null) {
			Object value = constantNode.getAttribute(ICodeKeyImpl.VALUE);
			if (constantSet.contains(value)) {
				errorHandler.flag(token, PascalErrorCode.CASE_CONSTANT_REUSED, this);
			} else {
				constantSet.add(value);
			}
		}
		nextToken(); // consume the constant
		return constantNode;
	}

	private ICodeNode parseCharacterConstant(Token token, String value, TokenType sign) {

		ICodeNode constantNode = null;
		if (sign != null) {
			errorHandler.flag(token, PascalErrorCode.INVALID_CONSTANT, this);
		} 
		else {
			if (value.length() == 1) {
				constantNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.STRING_CONSTANT);
				constantNode.setAttribute(ICodeKeyImpl.VALUE, value);
			} 
			else {
				errorHandler.flag(token, PascalErrorCode.INVALID_CONSTANT, this);
			}
		}
		
		return constantNode;
	}

	private ICodeNode parseIntegerConstant(String text, TokenType sign) {
		ICodeNode constantNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.INTEGER_CONSTANT);
		int intValue = Integer.parseInt(text);

		if (sign == PascalTokenType.MINUS) {
			intValue = -intValue;
		}

		constantNode.setAttribute(ICodeKeyImpl.VALUE, intValue);
		return constantNode;
	}

	private ICodeNode parseIdentifierConstant(Token token, TokenType sign) {
		errorHandler.flag(token, PascalErrorCode.INVALID_CONSTANT, this);
		return null;
	}

}
