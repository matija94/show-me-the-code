package frontend.pascal.parsers;

import java.util.EnumSet;

import frontend.Token;
import frontend.TokenType;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeKeyImpl;
import intermediate.icodeimpl.ICodeNodeTypeImpl;
import intermediate.ICodeNodeType;

public class ForStatementParser extends PascalParserTD {

	public ForStatementParser(PascalParserTD parent) {
		super(parent);
	}

	
	// synchronization for TO or DOWNTO
	static final EnumSet<PascalTokenType> TO_DOWNTO_SET = 
			EnumSet.copyOf(ExpressionParser.EXPR_START_SET);
	static {
		TO_DOWNTO_SET.add(PascalTokenType.TO);
		TO_DOWNTO_SET.add(PascalTokenType.DOWNTO);
		TO_DOWNTO_SET.addAll(ExpressionParser.STMT_FOLLOW_SET);
	}
	
	// synchronization for DO
	static final EnumSet<PascalTokenType> DO_SET =
			EnumSet.copyOf(ExpressionParser.STMT_START_SET);
	static {
		DO_SET.add(PascalTokenType.DO);
		DO_SET.addAll(ExpressionParser.STMT_FOLLOW_SET);
	}
	
	
	public ICodeNode parse(Token token) throws Exception {
		token = nextToken(); // consume FOR
		Token targetToken = token;
		
		// create compound and loop nodes
		ICodeNode compoundNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.COMPOUND);
		ICodeNode loopNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.LOOP);
				
		AssignmentStatementParser asp = new AssignmentStatementParser(this);
		ICodeNode initAssignNode = asp.parse(token);
		setLineNumber(initAssignNode, targetToken);
		
		compoundNode.addChild(initAssignNode); // init assing as first child
		compoundNode.addChild(loopNode); // loop node as second child
		
		// synchronize token at TO or DOWNTO
		token = synchronize(TO_DOWNTO_SET);
		TokenType direction = token.getType();
		
		// consume to or down to if existing
		if (direction == PascalTokenType.TO || direction == PascalTokenType.DOWNTO) {
			token = nextToken();
		}
		// flag an error for missing direction statment and use TO as default
		else {
			direction = PascalTokenType.TO;
			errorHandler.flag(token, PascalErrorCode.MISSING_TO_DOWNTO, this);
			
		}
		
		ICodeNode testNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.TEST);
		
		// this is rel op node of the test node which is performed in loop each time
		ICodeNode relOpNode = ICodeFactory.createICodeNode(direction==PascalTokenType.TO ? ICodeNodeTypeImpl.GT : ICodeNodeTypeImpl.LT);
		
		// control node is usually var i in the for loop
		ICodeNode controlVarNode = initAssignNode.getChildren().get(0);
		relOpNode.addChild(controlVarNode.copy());
	
		ExpressionParser ep = new ExpressionParser(this);
		relOpNode.addChild(ep.parse(token)); // parse expresion on the right side of the test
		
		testNode.addChild(relOpNode);
		loopNode.addChild(testNode);
		
		// now we start executing body of the loop
		
		token = synchronize(DO_SET);
		if (token.getType() == PascalTokenType.DO) {
			token = nextToken();
		}
		else {
			errorHandler.flag(token, PascalErrorCode.MISSING_DO, this);
		}
		
		// parse the nested statement and put it as second child of loop node
		StatementParser sp = new StatementParser(this);
		loopNode.addChild(sp.parse(token));
		
		// this is assign node which is responsible for incrementing/decrementing control var node
		ICodeNode nextAssignNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.ASSIGN);
		nextAssignNode.addChild(controlVarNode.copy()); // it's first child to hold the new computed value
		
		// compute node
		ICodeNode arithOpNode = ICodeFactory.createICodeNode(direction == PascalTokenType.TO ? ICodeNodeTypeImpl.ADD : ICodeNodeTypeImpl.SUBTRACT);
		arithOpNode.addChild(controlVarNode.copy());
		ICodeNode intOneNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.INTEGER_CONSTANT);
		intOneNode.setAttribute(ICodeKeyImpl.VALUE, 1);
		arithOpNode.addChild(intOneNode);
		
		nextAssignNode.addChild(arithOpNode); // add second child -- compute node
		
		loopNode.addChild(nextAssignNode);

		setLineNumber(nextAssignNode, targetToken);
		
		return compoundNode;
	}
	
	protected void setLineNumber(ICodeNode node, Token token) {
		if (node != null) {
			node.setAttribute(ICodeKeyImpl.LINE, token.getLineNum());
		}
	}
}
