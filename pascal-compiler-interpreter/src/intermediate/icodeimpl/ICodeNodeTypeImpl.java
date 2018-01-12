package intermediate.icodeimpl;

import intermediate.ICodeNodeType;

public enum ICodeNodeTypeImpl implements ICodeNodeType {

	
	// program structure
	PROGRAM, PROCEDURE, FUNCTION,
	
	// statements
	COMPOUND, ASSIGN, LOOP, TEST, CALL, PARAMETERS,
	IF, SELECT, SELECT_BRANCH, SELECT_CONSTANTS, NO_OP,
	
	// relational operators
	EQ, NE, LT, LE, GT, GE, NOT,
	
	// additive operators
	ADD, SUBTRACT, OR, NEGATE,
	
	// multiplicative operators
	MULTIPLY,INTEGER_DIVIDE,FLOAT_DIVIDE,MOD,AND,
	
	// operands
	VARIABLE, SUBSCRIPTS, FIELD,
	INTEGER_CONSTANT, REAL_CONSTANT,
	STRING_CONSTANT, BOOLEAN_CONSTANT
	;
}
