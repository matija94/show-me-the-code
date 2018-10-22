package frontend.pascal.parsers;

import frontend.Token;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import static frontend.pascal.PascalTokenType.*;

import java.util.EnumSet;

public class DeclarationParser extends PascalParserTD {

    public DeclarationParser(PascalParserTD parent) {
        super(parent);
    }

    static final EnumSet<PascalTokenType> DECLARATION_START_SET = EnumSet.of(CONST, TYPE, VAR, PROCEDURE, FUNCTION, BEGIN);

    static final EnumSet<PascalTokenType> TYPE_START_SET = DECLARATION_START_SET.clone();
    static {
        TYPE_START_SET.remove(CONST);
    }

    static final EnumSet<PascalTokenType> VAR_START_SET = TYPE_START_SET.clone();
    static {
        VAR_START_SET.remove(TYPE);
    }

    static final EnumSet<PascalTokenType> ROUTINE_START_SET = VAR_START_SET.clone();
    static {
        ROUTINE_START_SET.remove(VAR);
    }

    public void parse(Token token) throws Exception {
        token = synchronize(DECLARATION_START_SET);

        if (token.getType() == CONST) {
            token = nextToken();
            ConstantDefinitionParser cdp = new ConstantDefinitionParser(this);
            cdp.parse(token);
        }

        token = synchronize(TYPE_START_SET);

        if (token.getType() == TYPE) {
            token = nextToken();
            TypeDefinitionParser tdp = new TypeDefinitionParser(this);
            tdp.parse(token);
        }

        token = synchronize(VAR_START_SET);

        if (token.getType() == VAR) {
            token = nextToken();
            VariableDeclarationParser vdp = new VariableDeclarationParser(this);
            vdp.parse(token);
        }

        token = synchronize(ROUTINE_START_SET);
    }
}
