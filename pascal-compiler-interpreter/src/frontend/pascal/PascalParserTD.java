package frontend.pascal;

import java.io.IOException;
import java.util.EnumSet;

import frontend.EofToken;
import frontend.Parser;
import frontend.Scanner;
import frontend.Token;
import frontend.pascal.parsers.BlockParser;
import frontend.pascal.parsers.StatementParser;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.SymTabEntry;
import intermediate.symtabimpl.DefinitionImpl;
import intermediate.symtabimpl.Predefined;
import intermediate.symtabimpl.SymTabKeyImpl;
import messages.Message;
import messages.MessageType;

/**
 * TOP-DOWN Pascal parser
 *
 */
public class PascalParserTD extends Parser {

	protected static PascalErrorHandler errorHandler = new PascalErrorHandler();

	private SymTabEntry routineId;

	public PascalParserTD(Scanner scanner) {
		super(scanner);
	}
	
	/**
	 * Constructor for subclasses
	 * @param parent the parent parser
	 */
	public PascalParserTD(PascalParserTD parent) {
		super(parent.getScanner());
	}
	
	@Override
	public void parse() throws Exception {
		long startTime = System.currentTimeMillis();

		iCode = ICodeFactory.createICode();
		Predefined.initialize(symTabStack);

		routineId = symTabStack.enterLocal("dummyprogramname");
		routineId.setDefinition(DefinitionImpl.PROGRAM);
		symTabStack.setProgramId(routineId);

		routineId.setAttribute(SymTabKeyImpl.ROUTINE_SYMTAB, symTabStack.push());
		routineId.setAttribute(SymTabKeyImpl.ROUTINE_ICODE, iCode);

		BlockParser bp = new BlockParser(this);

		try {
			Token token = nextToken();

			ICodeNode rootNode = bp.parse(token, routineId);
			iCode.setRoot(rootNode);
			symTabStack.pop();

			token = currentToken();
			if (token.getType() != PascalTokenType.DOT) {
				errorHandler.flag(token, PascalErrorCode.MISSING_PERIOD, this);
			}
			token = currentToken();

			float elapsedTime = (System.currentTimeMillis() - startTime) / 1000f;
			sendMessage(new Message(MessageType.PARSER_SUMMARY,
					new Number[] {
							token.getLineNum(),
							getErrorCount(),
							elapsedTime
					}));
		}
		catch (Exception ex) {
			errorHandler.abortTranslation(PascalErrorCode.IO_ERROR, this);
		}
	}
	
	
	public Token synchronize(EnumSet<PascalTokenType> syncSet) throws Exception{
		Token token = currentToken();
		
		// if the current token is not in the sync set then it is unexpected and parser must recover
		if (!syncSet.contains(token.getType())) {
			
			// flag unexpected token
			errorHandler.flag(token, PascalErrorCode.UNEXPECTED_TOKEN, this);
			
			// recover by skipping tokens that are not in the sync set
			do {
				token = nextToken();
			} while (!(token instanceof EofToken) && !syncSet.contains(token.getType()));
		}
		
		return token;
	}
	
	
	/**
	* Return the number of syntax errors found by the parser.
	* @return the error count.
	*/
	@Override
	public int getErrorCount() {
		// TODO Auto-generated method stub
		return errorHandler.getErrorCount();
	}

}
