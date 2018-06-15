package intermediate;

/**
 * The interface for symbol table stack
 *
 */
public interface SymTabStack {
	
	/**
	 * Getter for current nesting level
	 * @return integer representing current nesting level
	 */
	public int getCurrentNestingLevel();

	/**
	 * Return the local symbol table which is on top of the stack
	 * @return symtab on from top of the stack
	 */
	public SymTab getLocalSymTab();
	
	/**
	 * Create and enter a new entry in the local symbol table
	 * @param name the name of entry
	 * @return new entry
	 */
	public SymTabEntry enterLocal(String name);

	/**
	 * Look up an existing symbol table entry in the local symbol table
	 * @param name the name of entry
	 * @return found entry or null if it doesn't exist
	 */
	public SymTabEntry lookupLocal(String name);
	
	/**
	 * Look up an existing symbol table entry throughout the entire stack.
	 * @param name the name of entry
	 * @return found entry or null if it doesn't exist
	 */
	public SymTabEntry lookup(String name);

	/**
	 * Setter.
	 * @param entry the symbol table entry for the main program identifier
	 */
	public void setProgramId(SymTabEntry entry);


	public SymTabEntry getProgramId();


	public SymTab push();


	public SymTab push(SymTab symTab);


	public SymTab pop();
	
}
