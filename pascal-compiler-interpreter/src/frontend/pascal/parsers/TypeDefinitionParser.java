package frontend.pascal.parsers;

import frontend.Token;
import frontend.TokenType;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.SymTabEntry;
import intermediate.TypeSpec;
import intermediate.symtabimpl.DefinitionImpl;

import static frontend.pascal.PascalTokenType.*;

import java.util.EnumSet;

public class TypeDefinitionParser extends DeclarationParser {


    public TypeDefinitionParser(PascalParserTD parent) {
        super(parent);
    }

    private static final EnumSet<PascalTokenType> IDENTIFIER_SET = DeclarationParser.VAR_START_SET.clone();
    static {
        IDENTIFIER_SET.add(IDENTIFIER);
    }

    private static final EnumSet<PascalTokenType> EQUALS_SET = ConstantDefinitionParser.CONSTANT_START_SET.clone();
    static {
        EQUALS_SET.add(EQUALS);
        EQUALS_SET.add(SEMICOLON);
    }

    private static final EnumSet<PascalTokenType> FOLLOW_SET = EnumSet.of(SEMICOLON);

    private static final EnumSet<PascalTokenType> NEXT_START_SET = DeclarationParser.VAR_START_SET.clone();
    static {
        NEXT_START_SET.add(SEMICOLON);
        NEXT_START_SET.add(IDENTIFIER);
    }

    public void parse(Token token) throws Exception {
        token = synchronize(IDENTIFIER_SET);

        while (token.getType() == IDENTIFIER) {
            String name = token.getText().toLowerCase();
            SymTabEntry typeId = symTabStack.lookupLocal(name);

            if (typeId == null) {
                typeId = symTabStack.enterLocal(name);
                typeId.appendLineNumber(token.getLineNum());
            }
            else {
                errorHandler.flag(token, PascalErrorCode.IDENTIFIER_UNDEFINED, this);
                typeId = null;
            }

            token = nextToken();

            token = synchronize(EQUALS_SET);
            if (token.getType() == EQUALS) {
                token = nextToken();
            }
            else {
                errorHandler.flag(token, PascalErrorCode.MISSING_EQUALS, this);
            }

            TypeSpecificationParser tsp = new TypeSpecificationParser(this);
            TypeSpec type = tsp.parse(token);

            if (typeId != null) {
                typeId.setDefinition(DefinitionImpl.TYPE);
            }

            if ((type != null) && (typeId != null)) {
                if (type.getIdentifier() == null) {
                    type.setIdentifier(typeId);
                }
                typeId.setTypeSpec(type);
            }
            else {
                token = synchronize(FOLLOW_SET);
            }

            token = currentToken();
            TokenType tokenType = token.getType();

            if (tokenType == SEMICOLON) {
                while(token.getType() == SEMICOLON) {
                    token = nextToken();
                }
            }
            else if (NEXT_START_SET.contains(tokenType)) {
                errorHandler.flag(token, PascalErrorCode.MISSING_SEMICOLON, this);
            }

            token = synchronize(IDENTIFIER_SET);
        }
    }
}
