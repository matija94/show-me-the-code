package frontend.pascal.parsers;

import frontend.Token;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.TypeSpec;

import static frontend.pascal.PascalTokenType.*;
import java.util.EnumSet;

public class TypeSpecificationParser extends TypeDefinitionParser {

    public TypeSpecificationParser(PascalParserTD parent) {
        super(parent);
    }

    static final EnumSet<PascalTokenType> TYPE_START_SET = SimpleTypeParser.SIMPLE_TYPE_START_SET.clone();
    static {
        TYPE_START_SET.add(ARRAY);
        TYPE_START_SET.add(RECORD);
        TYPE_START_SET.add(SEMICOLON);
    }


    public TypeSpec parse(Token token) throws Exception {
        token = synchronize(TYPE_START_SET);

        switch ((PascalTokenType) token.getType()) {
            case ARRAY: {
                ArrayTypeParser atp = new ArrayTypeParser(this);
                return atp.parse(token);
            }
            case RECORD: {
                RecordTypeParser rtp = new RecordTypeParser(this);
                return rtp.parse(token);
            }
            default: {
                SimpleTypeParser stp = new SimpleTypeParser(this);
                return stp.parse(token);
            }
        }
    }
}
