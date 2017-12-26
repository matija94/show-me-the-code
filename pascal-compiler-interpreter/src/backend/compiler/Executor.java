package backend.compiler;

import backend.Backend;
import intermediate.ICode;
import intermediate.SymTab;
import messages.Message;
import messages.MessageType;

public class Executor extends Backend{

	/**
	 * Process the intermediate code and the symbol table generated by the parser to
	 * execute the source program.
	 * 
	 * @param iCode
	 *            the intermediate code.
	 * @param symTab
	 *            the symbol table.
	 * @throws Exception
	 *             if an error occurred.
	 */
	public void process(ICode iCode, SymTab symTab) throws Exception {
		long startTime = System.currentTimeMillis();
		float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
		int executionCount = 0;
		int runtimeErrors = 0;
		
		// Send the interpreter summary message.
		sendMessage(new Message(MessageType.INTERPRETER_SUMMARY, 
								new Number[] { executionCount, runtimeErrors, elapsedTime }));
	}
}
