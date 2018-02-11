package backend.interpreter;

import backend.Backend;
import intermediate.ICodeNode;
import intermediate.icodeimpl.ICodeKeyImpl;
import messages.Message;
import messages.MessageType;

public class RuntimeErrorHandler {

	
	private static final int MAX_ERRORS = 0;
	
	private static int errorCount = 0;
	
	
	public void flag(ICodeNode node, RuntimeErrorCode errorCode, Backend backend) {
		
		String lineNumber = null;
		
		
		// search for ancestor node which has line number set
		while ( (node!=null) && (node.getAttribute(ICodeKeyImpl.LINE) == null)) {
			node = node.getParent();
		}
		
		backend.sendMessage(
				new Message(MessageType.RUNTIME_ERROR,
				
				new Object[] {errorCode.toString(), (Integer) node.getAttribute(ICodeKeyImpl.LINE)}));
		
	
		if (++errorCount > MAX_ERRORS) {
			System.out.println("*** ABORTED AFTER TOO MANY RUNTIME ERRORS. ");
			System.exit(-1);
		}
	}
	
	public int getErrorCount() {
		return errorCount;
	}
}
