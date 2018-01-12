package intermediate;

import intermediate.symtabimpl.SymTabEntryImpl;
import intermediate.symtabimpl.SymTabImpl;
import intermediate.symtabimpl.SymTabStackImpl;

public class SymTabFactory {

	/**
	 * Create and return symbol table stack implementation
	 * @return symbol table stack implementation
	 */
	public static SymTabStack createSymTabStack() {
		return new SymTabStackImpl();
	}
	
	/**
	 * Create and return symbol table implementation
	 * @param nestingLevel the nesting level
	 * @return the symbol table implementation
	 */
	public static SymTab createSymTab(int nestingLevel) {
		return new SymTabImpl();
	}
	
	/**
	* Create and return a symbol table entry implementation.
	* @param name the identifier name.
	* @param symTab the symbol table that contains this entry.
	* @return the symbol table entry implementation.
	*/
	public static SymTabEntry createSymTabEntry(String name, SymTab symTab) {
		return new SymTabEntryImpl(name, symTab);
	}
	
}
