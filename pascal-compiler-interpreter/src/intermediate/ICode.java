package intermediate;

public interface ICode {

	/**
	* Set and return the root node.
	* @param node the node to set as root.
	* @return the root node.
	*/
	public ICodeNode setRoot(ICodeNode node);
	
	/**
	* Get the root node.
	* @return the root node.
	*/
	public ICodeNode getRoot();
	
}
