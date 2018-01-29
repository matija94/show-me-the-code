package frontend.pascal.parsers;

import java.util.HashMap;

import frontend.Token;
import frontend.TokenType;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeKey;
import intermediate.ICodeNode;
import intermediate.ICodeNodeType;
import intermediate.SymTabEntry;
import intermediate.icodeimpl.ICodeKeyImpl;
import intermediate.icodeimpl.ICodeNodeTypeImpl;

public class ExpressionParser extends StatementParser {

	public ExpressionParser(PascalParserTD parent) {
		super(parent);
	}

	
	@Override
	public ICodeNode parse(Token token) throws Exception {
		return parseExpression(token);
	}
	
	private static final HashMap<PascalTokenType, ICodeNodeType> REL_OPS_MAP =
			new HashMap<>();
	
	static {
		REL_OPS_MAP.put(PascalTokenType.EQUALS, ICodeNodeTypeImpl.EQ);
		REL_OPS_MAP.put(PascalTokenType.NOT_EQUALS, ICodeNodeTypeImpl.NE);
		REL_OPS_MAP.put(PascalTokenType.LESS_THAN, ICodeNodeTypeImpl.LT);
		REL_OPS_MAP.put(PascalTokenType.LESS_EQUALS, ICodeNodeTypeImpl.LE);
		REL_OPS_MAP.put(PascalTokenType.GREATER_THAN, ICodeNodeTypeImpl.GT);
		REL_OPS_MAP.put(PascalTokenType.GREATER_EQUALS, ICodeNodeTypeImpl.GE);
	}
	
	private ICodeNode parseExpression(Token token) throws Exception {
		ICodeNode rootNode = parseSimpleExpression(token);
	
		token = currentToken();
		TokenType type = token.getType();
	
		// look for relational operator
		if (REL_OPS_MAP.containsKey(type)) {
			ICodeNodeType nodeType = REL_OPS_MAP.get(type);
			ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
			opNode.addChild(rootNode);
			
			token = nextToken(); // consume operator token
			
			// parse second simple expression
			// operator node adopts second simple expression as it's second child
			opNode.addChild(parseSimpleExpression(token));
			
			rootNode = opNode;
		}
		
		return rootNode;
	}

	
	private static final HashMap<PascalTokenType, ICodeNodeType> ADD_OPS_MAP =
			new HashMap<>();
	
	static {
		ADD_OPS_MAP.put(PascalTokenType.PLUS, ICodeNodeTypeImpl.ADD);
		ADD_OPS_MAP.put(PascalTokenType.MINUS, ICodeNodeTypeImpl.SUBTRACT);
		ADD_OPS_MAP.put(PascalTokenType.OR, ICodeNodeTypeImpl.OR);
	}

	private ICodeNode parseSimpleExpression(Token token) throws Exception {
		TokenType signType = null;
		TokenType type = token.getType();
		if (type == PascalTokenType.MINUS || type == PascalTokenType.PLUS) {
			signType = type;
			token = nextToken(); // consume sign token
		}
		
		ICodeNode rootNode = parseTerm(token);
		
		if (signType == PascalTokenType.MINUS) {
			ICodeNode negateNode = ICodeFactory.createICodeNode(ADD_OPS_MAP.get(signType));
			negateNode.addChild(rootNode);
			rootNode = negateNode;
		}
		
		token = currentToken();
		type = token.getType();
		
		while (ADD_OPS_MAP.containsKey(type)) {
			ICodeNode opNode = ICodeFactory.createICodeNode(ADD_OPS_MAP.get(type));
			opNode.addChild(rootNode);
			
			token = nextToken(); // consume operator token
			
			// parse another term and add it as second child to op node
			opNode.addChild(parseTerm(token));
			
			rootNode = opNode;
			
			token = currentToken();
			type = token.getType();
		}
		return rootNode;
	}

	private static final HashMap<PascalTokenType, ICodeNodeType> MULTI_OPS_MAP = new 
			HashMap<>();
	
	static {
		MULTI_OPS_MAP.put(PascalTokenType.STAR, ICodeNodeTypeImpl.MULTIPLY);
		MULTI_OPS_MAP.put(PascalTokenType.SLASH, ICodeNodeTypeImpl.FLOAT_DIVIDE);
		MULTI_OPS_MAP.put(PascalTokenType.DIV, ICodeNodeTypeImpl.INTEGER_DIVIDE);
		MULTI_OPS_MAP.put(PascalTokenType.MOD, ICodeNodeTypeImpl.MOD);
		MULTI_OPS_MAP.put(PascalTokenType.AND, ICodeNodeTypeImpl.AND);
	}
	
	private ICodeNode parseTerm(Token token) throws Exception {
		
		ICodeNode rootNode = parseFactor(token);
		
		token = currentToken();
		TokenType type = token.getType();
		
		while(MULTI_OPS_MAP.containsKey(type)) {
			ICodeNodeType nodeType = MULTI_OPS_MAP.get(type);
			ICodeNode opNode = ICodeFactory.createICodeNode(nodeType);
			opNode.addChild(rootNode);
			
			token = nextToken(); // consume op token
			
			// parse factor on the right side of op
			// and put it as second child of op
			opNode.addChild(parseFactor(token));
			
			rootNode = opNode;
			
			token = currentToken();
			type = token.getType();
		}
		
		return rootNode;
	}


	private ICodeNode parseFactor(Token token) throws Exception{

		TokenType type = token.getType();
		ICodeNode rootNode = null;
		
		switch ((PascalTokenType) type) {
			case IDENTIFIER: {
				// lookup identifier in the symbol table stack
				//flag identifier as undefined if it's pos is not found
				String name = token.getText().toLowerCase();
				SymTabEntry id = symTabStack.lookup(name);
				if (id == null) {
					errorHandler.flag(token, PascalErrorCode.IDENTIFIER_UNDEFINED, this);
					id = symTabStack.enterLocal(name);
				}
				
				rootNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.VARIABLE);
				rootNode.setAttribute(ICodeKeyImpl.ID, id);
				id.appendLineNumber(token.getLineNum());
				
				token = nextToken();
				break;
			}
			
			case INTEGER: {
				rootNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.INTEGER_CONSTANT);
				rootNode.setAttribute(ICodeKeyImpl.VALUE, token.getValue());
				
				token = nextToken();
				break;
			}
			
			case REAL: {
				rootNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.REAL_CONSTANT);
				rootNode.setAttribute(ICodeKeyImpl.VALUE, token.getValue());
				
				token = nextToken();
				break;
			}
			
			case STRING: {
				String value = (String) token.getValue();
				rootNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.STRING_CONSTANT);
				rootNode.setAttribute(ICodeKeyImpl.VALUE, value);
				
				token = nextToken();
				break;
			}
			
			case NOT: {
				// consume the not
				token = nextToken();
				
				rootNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.NOT);
				
				rootNode.addChild(parseFactor(token));
				
				break;
			}
			
			case LEFT_PAREN : {
				token = nextToken(); // consume the ( 
				
				// parse expression
				rootNode = parseExpression(token);
				
				token = currentToken();
				if (token.getType() == PascalTokenType.RIGHT_PAREN) {
					token = nextToken();
				}
				else {
					errorHandler.flag(token, PascalErrorCode.MISSING_RIGHT_PAREN, this);
				}
				
				break;
			}
			
			default: {
				errorHandler.flag(token, PascalErrorCode.UNEXPECTED_TOKEN, this);
				break;
			}
		}
		return rootNode;
	}
}
