package intermediate;

import java.util.ArrayList;

public interface ICodeNode {

	/**
	* Return the parent of this node.
	* @return the parent node.
	*/
	public ICodeNode getParent();
	
	/**
	* Getter.
	* @return the node type.
	*/
	public ICodeNodeType getType();
	
	/**
	* Add a child node.
	* @param node the child node.
	* @return the child node.
	*/
	public ICodeNode addChild(ICodeNode node);
	
	/**
	* Return an array list of this node&apos;s children.
	* @return the array list of children.
	*/
	public ArrayList<ICodeNode> getChildren();
	
	/**
	* Set a node attribute.
	* @param key the attribute key.
	* @param value the attribute value.
	*/
	public void setAttribute(ICodeKey key, Object value);

	/**
	* Get the value of a node attribute.
	* @param key the attribute key.
	* @return the attribute value.
	*/
	public Object getAttribute(ICodeKey key);
	
	/**
	* Make a copy of this node.
	* @return the copy.
	*/
	public ICodeNode copy();
}
