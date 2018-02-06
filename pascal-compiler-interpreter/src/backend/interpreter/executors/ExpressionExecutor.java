package backend.interpreter.executors;

import java.util.ArrayList;
import java.util.EnumSet;

import backend.interpreter.Executor;
import backend.interpreter.RuntimeErrorCode;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeKeyImpl;
import intermediate.icodeimpl.ICodeNodeTypeImpl;
import intermediate.symtabimpl.SymTabEntryImpl;
import intermediate.symtabimpl.SymTabKeyImpl;

public class ExpressionExecutor extends Executor {

	public ExpressionExecutor(Executor parent) {
		super(parent);
	}

	
	public Object execute(ICodeNode node) {

		ICodeNodeTypeImpl type = (ICodeNodeTypeImpl) node.getType();
		
		switch(type) {
			
			case VARIABLE: {
				SymTabEntryImpl variableId =(SymTabEntryImpl) node.getAttribute(ICodeKeyImpl.ID);
				return variableId.get(SymTabKeyImpl.DATA_VALUE);
			}
			
			case INTEGER_CONSTANT: {
				return (Integer) node.getAttribute(ICodeKeyImpl.VALUE);
			}
			
			case REAL_CONSTANT: {
				return (Float) node.getAttribute(ICodeKeyImpl.VALUE);
			}
			
			case STRING_CONSTANT: {
				return (String) node.getAttribute(ICodeKeyImpl.VALUE);
			}
			
			case NEGATE: {
				ArrayList<ICodeNode> children = node.getChildren();
			
				ICodeNode expressionNode = children.get(0);
				
				Object value = execute(expressionNode);
				if (value instanceof Integer) {
					return -((Integer) value);
				}
				else {
					return -((Float) value);
				}
			}
			
			case NOT: {
				ArrayList<ICodeNode> children = node.getChildren();
				ICodeNode expressionNode = children.get(0);
				
				boolean value = (Boolean) execute(expressionNode);
				return !value;
			}
			
			default: return executeBinaryOperator(node);
			
		}
	}

	
	private static final EnumSet<ICodeNodeTypeImpl> ARITH_OPS = 
			EnumSet.of(ICodeNodeTypeImpl.ADD, ICodeNodeTypeImpl.SUBTRACT, ICodeNodeTypeImpl.MULTIPLY, ICodeNodeTypeImpl.INTEGER_DIVIDE, ICodeNodeTypeImpl.FLOAT_DIVIDE);

	
	/**
	 * Execute a expression tree with root representing binary operator.
	 * @param node
	 * @param type
	 * @return
	 */
	private Object executeBinaryOperator(ICodeNode node) {
	
		// get two operand node which are children of binary_op node
		ArrayList<ICodeNode> children = node.getChildren();
		ICodeNode operandNode1 = children.get(0);
		ICodeNode operandNode2 = children.get(1);
	
		// operands
		Object operand1 = execute(operandNode1);
		Object operand2 = execute(operandNode2);
	
		ICodeNodeTypeImpl type = (ICodeNodeTypeImpl) node.getType();
		
		boolean integerMode = (operand1 instanceof Integer) && (operand2 instanceof Integer);
		
		if (ARITH_OPS.contains(type)) {
			if(integerMode) {
				int value1 = (int) operand1;
				int value2 = (int) operand2;
				
				switch (type) {
					case ADD: return value1 + value2;
					case SUBTRACT: return value1 - value2;
					case MULTIPLY: return value1 * value2;
					
					case FLOAT_DIVIDE: {
						
						if (value2 != 0) {
							return ((float)value1)/((float)value2);
						}
						else {
							errorHandler.flag(node, RuntimeErrorCode.DIVISION_BY_ZERO, this);
							return 0;
						}
					}
					
					case INTEGER_DIVIDE: {
						
						if (value2 != 0) {
							return value1/value2;
						}
						else {
							errorHandler.flag(node, RuntimeErrorCode.DIVISION_BY_ZERO, this);
							return 0;
						}
					}
					
					case MOD: {
						
						if (value2 != 0) {
							return value1%value2;
						}
						else {
							errorHandler.flag(node, RuntimeErrorCode.DIVISION_BY_ZERO, this);
							return 0;
						}
					}
					default: {
						System.out.println("SYSTEM ERROR -- SHUTTING DOWN");
						System.exit(-1);
					}
				}
			}
			else {
				float value1 = operand1 instanceof Integer ? (Integer) operand1 : (Float) operand1;
				float value2 = operand2 instanceof Integer ? (Integer) operand1 : (Float) operand2;
				
				switch (type) {
					case ADD: return value1 + value2;
					case SUBTRACT: return value1 - value2;
					case MULTIPLY: return value1 * value2;
					
					case FLOAT_DIVIDE: {
						
						if (value2 != 0.0f) {
							return value1/value2;
						}
						else {
							errorHandler.flag(node, RuntimeErrorCode.DIVISION_BY_ZERO, this);
							return 0;
						}
					}
						
					default: {
						System.out.println("SYSTEM ERROR -- SHUTTING DOWN");
						System.exit(-1);
					}
				}
			}
		}
		
		else if (type == ICodeNodeTypeImpl.ADD || type == ICodeNodeTypeImpl.OR) {
			boolean value1 = (Boolean) operand1;
			boolean value2 = (Boolean) operand2;
			
			switch (type) {
				case AND: return value1 && value2;
				case OR: return value1 || value2;
				default: {
					System.out.println("SYSTEM ERROR -- SHUTTING DOWN");
					System.exit(-1);
				}
			}
		}
		
		// RELATIONAL OPERATORS
		else if (integerMode) {
			int value1 = (Integer) operand1;
			int value2 = (Integer) operand2;
			
			switch (type) {
				case EQ: return value1 == value2;
				case NE: return value1 != value2;
				case LT: return value1 < value2;
				case LE: return value1 <= value2;
				case GT: return value1 > value2;
				case GE: return value1 >= value2;
				default : {
					System.out.println("SYSTEM ERROR -- SHUTTING DOWN");
					System.exit(-1);
				}
			}
		}
		
		// float operands -- relational operator
		else {
			float value1 = operand1 instanceof Integer ? (Integer) operand1 : (Float) operand1;
			float value2 = operand2 instanceof Integer ? (Integer) operand2 : (Float) operand2;
			
			
			switch (type) {
				case EQ: return value1 == value2;
				case NE: return value1 != value2;
				case LT: return value1 < value2;
				case LE: return value1 <= value2;
				case GT: return value1 > value2;
				case GE: return value1 >= value2;
				default : {
					System.out.println("SYSTEM ERROR -- SHUTTING DOWN");
					System.exit(-1);
				}
			}
		}
		
		System.out.println("SYSTEM ERROR -- SHUTTING DOWN");
		System.exit(-1);
		return 0;
	}
}
