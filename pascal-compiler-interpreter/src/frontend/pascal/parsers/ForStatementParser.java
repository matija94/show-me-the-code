package frontend.pascal.parsers;

import java.util.EnumSet;

import frontend.Token;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeNode;

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
		
		
	}
}
