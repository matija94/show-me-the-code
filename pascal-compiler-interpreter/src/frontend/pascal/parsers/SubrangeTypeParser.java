package frontend.pascal.parsers;

import frontend.Token;
import frontend.TokenType;
import frontend.pascal.PascalErrorCode;
import frontend.pascal.PascalParserTD;
import frontend.pascal.PascalTokenType;
import intermediate.TypeFactory;
import intermediate.TypeSpec;
import intermediate.symtabimpl.Predefined;
import intermediate.typeimpl.TypeFormImpl;
import intermediate.typeimpl.TypeKeyImpl;

public class SubrangeTypeParser extends TypeSpecificationParser {

    public SubrangeTypeParser(PascalParserTD parent) {
        super(parent);
    }

    @Override
    public TypeSpec parse(Token token) throws Exception {
        TypeSpec subrangeType = TypeFactory.createType(TypeFormImpl.SUBRANGE);
        Object minVal = null;
        Object maxVal = null;

        Token constantToken = null;
        ConstantDefinitionParser cdp = new ConstantDefinitionParser(this);
        minVal = cdp.parseConstant(token);

        TypeSpec minType = constantToken.getType() == PascalTokenType.IDENTIFIER ? cdp.getConstantType(constantToken) : cdp.getConstantType(minVal);
        minVal = checkValueType(constantToken, minVal, minType);

        token = currentToken();
        Boolean sawDotDot = false;

        if (token.getType() == PascalTokenType.DOT_DOT) {
            token = nextToken();
            sawDotDot = true;
        }

        TokenType tokenType = token.getType();

        if (ConstantDefinitionParser.CONSTANT_START_SET.contains(tokenType)) {
            if (!sawDotDot) {
                errorHandler.flag(token, PascalErrorCode.MISSING_DOT_DOT, this);
            }

            token = synchronize(ConstantDefinitionParser.CONSTANT_START_SET);
            constantToken = token;
            maxVal = cdp.parseConstant(token);

            TypeSpec maxType = constantToken.getType() == PascalTokenType.IDENTIFIER ? cdp.getConstantType(constantToken) : cdp.getConstantType(maxVal);

            if ((minType == null) || (maxType == null)) {
                errorHandler.flag(token, PascalErrorCode.INCOMPATIBLE_TYPES, this);
            }

            else if (minType != maxType) {
                errorHandler.flag(constantToken, PascalErrorCode.INVALID_SUBRANGE_TYPE, this);
            }

            else if ((minVal != null) && (maxVal != null) && ((Integer) minVal >= (Integer)maxVal)) {
                errorHandler.flag(constantToken, PascalErrorCode.MIN_GT_MAX, this);
            }
        }
        else {
            errorHandler.flag(constantToken, PascalErrorCode.INVALID_SUBRANGE_TYPE, this);
        }

        subrangeType.setAttribute(TypeKeyImpl.SUBRANGE_BASE_TYPE, minType);
        subrangeType.setAttribute(TypeKeyImpl.SUBRANGE_MIN_VALUE, minVal);
        subrangeType.setAttribute(TypeKeyImpl.SUBRAGE_MAX_VALUE, maxVal);

        return subrangeType;
    }

    private Object checkValueType(Token token, Object value, TypeSpec type) {
        if (type == null) {
            return value;
        }
        if (type == Predefined.integerType) {
            return value;
        }
        else if (type == Predefined.charType) {
            char ch = ((String) value).charAt(0);
            return Character.getNumericValue(ch);
        }
        else if (type.getForm() == TypeFormImpl.ENUMERATION) {
            return value;
        }
        else {
            errorHandler.flag(token, PascalErrorCode.INVALID_SUBRANGE_TYPE, this);
            return value;
        }
    }
}
