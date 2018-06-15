package backend.interpreter.executors;

import backend.interpreter.Executor;
import backend.interpreter.RuntimeErrorCode;
import intermediate.ICodeNode;
import intermediate.ICodeNodeType;
import intermediate.icodeimpl.ICodeKeyImpl;
import intermediate.icodeimpl.ICodeNodeImpl;
import intermediate.icodeimpl.ICodeNodeTypeImpl;
import messages.Message;
import messages.MessageType;

public class StatementExecutor extends Executor {

	public StatementExecutor(Executor parent) {
		super(parent);
	}

	/**
	* Execute a statement.
	* To be overridden by the specialized statement executor subclasses.
	* @param node the root node of the statement.
	* @return null.
	*/
	public Object execute(ICodeNode node) {
		
		ICodeNodeTypeImpl type = (ICodeNodeTypeImpl) node.getType();
	
		sendSourceLineMessage(node);
		
		switch (type) {
			case COMPOUND: {
				CompoundExecutor ce = new CompoundExecutor(this);
				return ce.execute(node);
				
			}
	
			case ASSIGN: {
				AssignmentExecutor ae = new AssignmentExecutor(this);
				return ae.execute(node);
			}

			case LOOP: {
				LoopExecutor loopExecutor = new LoopExecutor(this);
				return loopExecutor.execute(node);
			}

			case IF: {
				IfExecutor ifExecutor = new IfExecutor(this);
				return ifExecutor.execute(node);
			}

			case SELECT: {
				SelectExecutor selectExecutor = new SelectExecutor(this);
				return selectExecutor.execute(node);
			}

			case NO_OP: return null;
			
			default: {
				errorHandler.flag(node, RuntimeErrorCode.UNIMPLEMENTED_FEATURE, this);
				return null;
			}
		}
	}

	private void sendSourceLineMessage(ICodeNode node) {
		Object lineNumber = node.getAttribute(ICodeKeyImpl.LINE);
	
		if (lineNumber != null) {
			sendMessage(new Message(MessageType.SOURCE_LINE, lineNumber));
		}
	}
}
