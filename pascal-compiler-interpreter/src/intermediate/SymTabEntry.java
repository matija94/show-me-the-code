package intermediate;

import java.util.List;

public interface SymTabEntry {

	/**
	 * Getter.
	 * 
	 * @return the name of the entry.
	 */
	public String getName();

	/**
	 * Getter.
	 * 
	 * @return the symbol table that contains this entry.
	 */
	public SymTab getSymTab();

	/**
	 * Append a source line number to the entry.
	 * 
	 * @param lineNumber
	 *            the line number to append.
	 */
	public void appendLineNumber(int lineNumber);

	/**
	 * Getter.* @return the list of source line numbers.
	 */
	public List<Integer> getLineNumbers();

	/**
	 * Set an attribute of the entry.
	 * 
	 * @param key
	 *            the attribute key.
	 * @param value
	 *            the attribute value.
	 */
	public void setAttribute(SymTabKey key, Object value);

	/**
	 * Get the value of an attribute of the entry.
	 * 
	 * @param key
	 *            the attribute key.
	 * @return the attribute value.
	 */
	public Object getAttribute(SymTabKey key);


	public void setDefinition(Definition definition);


	public Definition getDefinition();


	public void setTypeSpec(TypeSpec typeSpec);


	public TypeSpec getTypeSpec();

}
