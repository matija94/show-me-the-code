package backend.interpreter.executors;

import java.util.ArrayList;

import backend.interpreter.Executor;
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
			
			default: return executeBinaryOperator(node, type);
			
		}
	}


	private Object executeBinaryOperator(ICodeNode node, ICodeNodeTypeImpl type) {
		return null;
	}
}
