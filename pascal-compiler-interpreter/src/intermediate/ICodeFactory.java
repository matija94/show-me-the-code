package intermediate;

import intermediate.icodeimpl.ICodeImpl;
import intermediate.icodeimpl.ICodeNodeImpl;

public class ICodeFactory {

	/**
	* Create and return an intermediate code implementation.
	* @return the intermediate code implementation.
	*/
	public static ICode createICode() {
		return new ICodeImpl();
	}
	
	/**
	* Create and return a node implementation.
	* @param type the node type.
	* @return the node implementation.
	*/
	public static ICodeNode createICodeNode(ICodeNodeType type) {
		return new ICodeNodeImpl(type);
	}
}
