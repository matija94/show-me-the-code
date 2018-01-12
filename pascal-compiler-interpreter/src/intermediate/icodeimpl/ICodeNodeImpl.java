package intermediate.icodeimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import intermediate.ICodeFactory;
import intermediate.ICodeKey;
import intermediate.ICodeNode;
import intermediate.ICodeNodeType;

public class ICodeNodeImpl extends HashMap<ICodeKey, Object> implements ICodeNode{

	private ICodeNodeType type;
	private ICodeNode parent;
	private ArrayList<ICodeNode> children;
	
	public ICodeNodeImpl(ICodeNodeType type) {
		this.type = type;
		this.children = new ArrayList<>();
	}
	
	@Override
	public ICodeNode getParent() {
		return parent;
	}

	@Override
	public ICodeNodeType getType() {
		return type;
	}

	@Override
	public ICodeNode addChild(ICodeNode node) {
		if (node != null) {
			children.add(node);
			((ICodeNodeImpl)node).parent = this;
		}
		
		return node;
	}

	@Override
	public ArrayList<ICodeNode> getChildren() {
		return children;
	}

	@Override
	public void setAttribute(ICodeKey key, Object value) {
		put(key, value);
	}

	@Override
	public Object getAttribute(ICodeKey key) {
		return get(key);
	}

	@Override
	public ICodeNode copy() {
		ICodeNodeImpl copy = (ICodeNodeImpl) ICodeFactory.createICodeNode(type);

		Set<Entry<ICodeKey, Object>> attributes = entrySet();
		Iterator<Entry<ICodeKey, Object>> iterator = attributes.iterator();
	
		while(iterator.hasNext()) {
			Entry<ICodeKey, Object> attribute = iterator.next();
			copy.put(attribute.getKey(), attribute.getValue());
		}
		
		return copy;
	}

	public String toString() {
		return type.toString();
	}
}
