package frontend.pascal.parsers;

import frontend.Token;
import frontend.TokenType;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.ICodeFactory;
import intermediate.ICodeNode;
import intermediate.SymTabEntry;
import intermediate.icodeimpl.ICodeNodeTypeImpl;

public class BlockParser extends PascalParserTD {

    public BlockParser(PascalParserTD parent) {
        super(parent);
    }

    public ICodeNode parse(Token token, SymTabEntry routineId) throws Exception{
        DeclarationsParser dp = new DeclarationsParser(this);
        StatementParser sp = new StatementParser(this);

        dp.parse(token);

        token = synchronize(StatementParser.STMT_START_SET);
        TokenType tokenType = token.getType();
        ICodeNode rootNode = null;

        if (tokenType == PascalTokenType.BEGIN) {
            rootNode = sp.parse(token);
        }

        else {
            errorHandler.flag(token, PascalErrorCode.MISSING_BEGIN, this);

            if (StatementParser.STMT_START_SET.contains(tokenType)) {
                rootNode = ICodeFactory.createICodeNode(ICodeNodeTypeImpl.COMPOUND);
                sp.parseList(token, rootNode, PascalTokenType.END, PascalErrorCode.MISSING_END);
            }
        }
        return rootNode;
    }
}
