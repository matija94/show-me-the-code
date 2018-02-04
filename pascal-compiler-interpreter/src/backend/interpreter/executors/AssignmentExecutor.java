package backend.interpreter.executors;

import java.util.ArrayList;

import backend.interpreter.Executor;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeKeyImpl;
import intermediate.symtabimpl.SymTabEntryImpl;
import intermediate.symtabimpl.SymTabKeyImpl;
import messages.Message;
import messages.MessageType;

public class AssignmentExecutor extends Executor {

	public AssignmentExecutor(Executor parent) {
		super(parent);
	}

	public Object execute(ICodeNode node) {
		
		ArrayList<ICodeNode> children = node.getChildren();
		ICodeNode variableNode = children.get(0);
		ICodeNode expressionNode = children.get(1);
		
		ExpressionExecutor ee = new ExpressionExecutor(this);
		Object value = ee.execute(expressionNode);
		
		SymTabEntryImpl variableId = (SymTabEntryImpl) variableNode.getAttribute(ICodeKeyImpl.ID);
		variableId.setAttribute(SymTabKeyImpl.DATA_VALUE, value);
		
		sendMessage(node, variableId.getName(), value);
		
		++executionCount;
		return null;
	}
	
	
	// send message about assignment operation
	private void sendMessage(ICodeNode node, String variableName, Object value) {
		Object lineNumber = node.getAttribute(ICodeKeyImpl.LINE);
		
		if (lineNumber != null) {
			sendMessage(new Message(MessageType.ASSIGN, 
					
						new Object[] {lineNumber, variableName, value}));
		}
	}
	
}
