import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import intermediate.ICode;
import intermediate.ICodeKey;
import intermediate.ICodeNode;
import intermediate.SymTabEntry;
import intermediate.icodeimpl.ICodeNodeImpl;

public class ParserTreePrinter {

	
	private static final int INDENT_WIDTH = 4;
	private static final int LINE_WIDTH = 80;

	
	private PrintStream ps;
	private int length;
	private String indent; // indent spaces
	private String indentation; // indentation of line
	private StringBuilder line;


	public ParserTreePrinter(PrintStream ps) {
		this.ps = ps;
		this.length = 0;
		this.indentation = "";
		this.line = new StringBuilder();
		
		this.indent = "";
		for(int i = 0; i < INDENT_WIDTH; ++i) {
			this.indent += " ";
		}
	}
	
	public void print(ICode iCode) {
		ps.println("\n=============== INTERMEDIATE CODE ====================\n");
		
		printNode((ICodeNodeImpl)iCode.getRoot());
		printLine();
	}
	
	private void printNode(ICodeNodeImpl node) {
		
		// opening tag
		append(indentation); append("<" + node.toString());
		
		printAttributes(node);
		//printTypeSpec(node);
		
		ArrayList<ICodeNode> childNodes = node.getChildren();
		
		if (childNodes != null && childNodes.size()>0) {
			append(">");
			printLine();
			
			printChildNodes(childNodes);
			append(indentation); append("</" + node.toString() + ">");
		}
		
		else {
			append(" "); append("/>");
		}
		
		printLine();
	}
	
	
	private void printChildNodes(ArrayList<ICodeNode> childNodes) {
		
		String saveIndentation = indentation;
		indentation += indent;
		
		for(ICodeNode child : childNodes) {
			printNode((ICodeNodeImpl)child);
		}
		indentation = saveIndentation;
	}

	private void printAttributes(ICodeNodeImpl node) {
		String saveIndentation = indentation;
		indentation+=indent;
		
		Set<Entry<ICodeKey, Object>> attributes = node.entrySet();
		Iterator<Entry<ICodeKey, Object>> iterator = attributes.iterator();
	
		while(iterator.hasNext()) {
			Entry<ICodeKey, Object> next = iterator.next();
			printAttribute(next.getKey().toString(), next.getValue());
		}
		indentation = saveIndentation;
	}

	private void printAttribute(String key, Object value) {
		// If the value is a symbol table entry, use the identifier's name.
		// Else just use the value string.
		boolean isSymTabEntry = value instanceof SymTabEntry;
		String valueString = isSymTabEntry ? ((SymTabEntry) value).getName() : value.toString();
	
		String text = key.toLowerCase() + "=\"" + valueString + "\"";
		append(" "); append(text);
		
		if (isSymTabEntry) {
			int level = ((SymTabEntry) value).getSymTab().getNestingLevel();
			printAttribute("LEVEL", level);
		}
	}
	
	





	private void append(String text) {
		int textLenght = text.length();
		boolean lineBreak = false;
	
		
		if (length + textLenght > LINE_WIDTH) {
			printLine();
			line.append(indentation);
			length = indentation.length();
			lineBreak = true;
		}
		
		if (!(lineBreak && text.equals(" "))) {
			line.append(text);
			length += textLenght;
		}
	}
	
	private void printLine() {
		if (length > 0) {
			ps.println(line);
			line.setLength(0);
			length = 0;
		}
	}
}
