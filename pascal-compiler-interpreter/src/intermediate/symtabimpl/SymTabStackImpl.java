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
		return null;
	}

}
