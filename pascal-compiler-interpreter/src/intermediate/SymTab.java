package intermediate;

import java.util.List;

/**
 * Interface which represents the symbol table
 */
public interface SymTab {
	
	/**
	 * Getter 
	 * @return the scope nesting level of this table
	 */
	public int getNestingLevel();
	
	/**
	 * Create and enter new entry in this symbol table
	 * @param name the name of new entry
	 * @return the new entry
	 */
	public SymTabEntry enter(String name);

	/**
	 * Look up an existing entry in this symbol table
	 * @param entry the name of entry
	 * @return found entry or null if it doesn't exist
	 */
	public SymTabEntry lookup(String entry);
	
	/**
	 * @return list of symbol table entries sorted by names
	 */
	public List<SymTabEntry> sortedEntries();
}
