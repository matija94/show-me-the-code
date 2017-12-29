package intermediate.symtabimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

import intermediate.SymTab;
import intermediate.SymTabEntry;
import intermediate.SymTabFactory;

public class SymTabImpl extends TreeMap<String, SymTabEntry> implements SymTab {

	@Override
	public int getNestingLevel() {
		return 0;
	}

	@Override
	public SymTabEntry enter(String name) {
		SymTabEntry entry = SymTabFactory.createSymTabEntry(name, this);
		
		put(name, entry);
		return entry;
	}

	@Override
	public SymTabEntry lookup(String entry) {
		SymTabEntry symTabEntry = get(entry);
		return symTabEntry;
	}

	@Override
	public List<SymTabEntry> sortedEntries() {
		Collection<SymTabEntry> entries = values();
		return new ArrayList<>(entries);
	}

}
