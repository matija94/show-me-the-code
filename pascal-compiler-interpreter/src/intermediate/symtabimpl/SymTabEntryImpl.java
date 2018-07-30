package intermediate.symtabimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import intermediate.*;

public class SymTabEntryImpl extends HashMap<SymTabKey, Object> implements SymTabEntry {

	private String name;
	// entry name
	private SymTab symTab;
	// parent symbol table
	private ArrayList<Integer> lineNumbers; // source line numbers

	private Definition definition; // definition of the indetifier (constant, type, variable)

	private TypeSpec typeSpec;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the name of the entry.
	 * @param symTab
	 *            the symbol table that contains this entry.
	 */
	public SymTabEntryImpl(String name, SymTab symTab) {
		this.name = name;
		this.symTab = symTab;
		this.lineNumbers = new ArrayList<Integer>();
	}

	/**
	 * Append a source line number to the entry.
	 * 
	 * @param lineNumber
	 *            the line number to append.
	 */
	public void appendLineNumber(int lineNumber) {
		lineNumbers.add(lineNumber);
	}

	/**
	 * Set an attribute of the entry.
	 * 
	 * @param key
	 *            the attribute key.
	 * @param value
	 *            the attribute value.
	 */
	public void setAttribute(SymTabKey key, Object value) {
		put(key, value);
	}

	/**
	 * Get the value of an attribute of the entry.
	 * 
	 * @param key
	 *            the attribute key.
	 * @return the attribute value.
	 */
	public Object getAttribute(SymTabKey key) {
		return get(key);
	}


	@Override
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}

	@Override
	public Definition getDefinition() {
		return definition;
	}

	@Override
	public void setTypeSpec(TypeSpec typeSpec) {
		this.typeSpec = typeSpec;
	}

	@Override
	public TypeSpec getTypeSpec() {
		return typeSpec;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public SymTab getSymTab() {
		return symTab;
	}

	@Override
	public List<Integer> getLineNumbers() {
		return lineNumbers;
	}

	@Override
	public String toString() {
		String dataVal = (String) getOrDefault(SymTabKeyImpl.DATA_VALUE, "NIL").toString();
		return String.format("%s = %s", name, dataVal);
	}
}
