package frontend.pascal.parsers;

import frontend.Token;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.Definition;
import intermediate.SymTabEntry;
import intermediate.TypeSpec;
import intermediate.symtabimpl.DefinitionImpl;

import static frontend.pascal.PascalTokenType.*;

import java.util.EnumSet;

public class SimpleTypeParser extends TypeSpecificationParser {

    public SimpleTypeParser(PascalParserTD parent) {
        super(parent);
    }

    static final EnumSet<PascalTokenType> SIMPLE_TYPE_START_SET = ConstantDefinitionParser.CONSTANT_START_SET.clone();
    static {
        SIMPLE_TYPE_START_SET.add(LEFT_PAREN);
        SIMPLE_TYPE_START_SET.add(COMMA);
        SIMPLE_TYPE_START_SET.add(SEMICOLON);
    }

    @Override
    public TypeSpec parse(Token token) throws Exception {
        token = synchronize(SIMPLE_TYPE_START_SET);

        switch ((PascalTokenType) token.getType()) {
            case IDENTIFIER: {
                String name = token.getText();
                SymTabEntry id = symTabStack.lookup(name);

                if (id != null) {
                    Definition definition = id.getDefinition();

                    if (definition == DefinitionImpl.TYPE) {
                        id.appendLineNumber(token.getLineNum());
                        token = nextToken();

                        return id.getTypeSpec();
                    }
                    else if ((definition != DefinitionImpl.CONSTANT) && (definition != DefinitionImpl.ENUMERATION_CONSTANT)) {
                        errorHandler.flag(token, PascalErrorCode.NOT_TYPE_IDENTIFIER, this);
                        token = nextToken();
                        return null;
                    }
                    else {
                        SubrangeTypeParser stp = new SubrangeTypeParser(this);
                        return stp.parse(token);
                    }
                }
                else {
                    errorHandler.flag(token, PascalErrorCode.IDENTIFIER_UNDEFINED, this);
                    token = nextToken();
                    return null;
                }
            }

            case LEFT_PAREN: {
                EnumerationTypeParser etp = new EnumerationTypeParser(this);
                return etp.parse(token);
            }

            case COMMA:
            case SEMICOLON: {
                errorHandler.flag(token, PascalErrorCode.INVALID_TYPE, this);
                return null;
            }

            default: {
                SubrangeTypeParser stp = new SubrangeTypeParser(this);
                return stp.parse(token);
            }
        }
    }
}
