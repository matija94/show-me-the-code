package intermediate.symtabimpl;

import java.util.ArrayList;

import intermediate.SymTab;
import intermediate.SymTabEntry;
import intermediate.SymTabFactory;
import intermediate.SymTabStack;

/**
 * An implementation of symbol table stack
 * @author matija
 *
 */
public class SymTabStackImpl extends ArrayList<SymTab> implements SymTabStack {

	private SymTabEntry programId; // entry for the main program id
	private int currentNestingLevel;
	
	public SymTabStackImpl() {
		this.currentNestingLevel = 0;
		add(SymTabFactory.createSymTab(currentNestingLevel));
	}
	
	@Override
	public int getCurrentNestingLevel() {
		return currentNestingLevel;
	}

	@Override
	public SymTab getLocalSymTab() {
		return get(currentNestingLevel);
	}

	@Override
	public SymTabEntry enterLocal(String name) {
		return getLocalSymTab().enter(name);
	}

	@Override
	public SymTabEntry lookupLocal(String name) {
		return getLocalSymTab().lookup(name);
	}

	@Override
	public SymTabEntry lookup(String name) {
		SymTabEntry foundEntry = null;

		for(int i = currentNestingLevel; (i>=0) && (foundEntry==null); --i) {
			foundEntry = get(i).lookup(name);
		}

		return foundEntry;
	}

	@Override
	public void setProgramId(SymTabEntry entry) {

	}

	@Override
	public SymTabEntry getProgramId() {
		return null;
	}

	@Override
	public SymTab push() {
		SymTab tab = SymTabFactory.createSymTab(++currentNestingLevel);
		add(tab);

		return tab;
	}

	@Override
	public SymTab push(SymTab symTab) {
		++currentNestingLevel;
		add(symTab);
		return symTab;
	}

	@Override
	public SymTab pop() {
		SymTab symTab = get(currentNestingLevel);
		remove(currentNestingLevel--);
		return symTab;
	}

}
