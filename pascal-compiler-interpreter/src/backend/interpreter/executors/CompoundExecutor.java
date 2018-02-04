package backend.interpreter.executors;

import java.util.ArrayList;

import backend.interpreter.Executor;
import intermediate.ICodeNode;

public class CompoundExecutor extends Executor {

	public CompoundExecutor(Executor parent) {
		super(parent);
	}

	public Object execute(ICodeNode node) {
		
		// loop over the children of the compound node and execute each.
		StatementExecutor se = new StatementExecutor(this);
		
		ArrayList<ICodeNode> children = node.getChildren();
		children.forEach(childNode -> se.execute(childNode));
		
		return null;
	}
	
}
