package intermediate.symtabimpl;

import intermediate.*;
import intermediate.typeimpl.TypeFormImpl;
import intermediate.typeimpl.TypeKeyImpl;

import java.util.ArrayList;

public class Predefined {

    public static TypeSpec integerType;
    public static TypeSpec realType;
    public static TypeSpec booleanType;
    public static TypeSpec charType;
    public static TypeSpec undefinedType;

    public static SymTabEntry integerId;
    public static SymTabEntry realId;
    public static SymTabEntry booleanId;
    public static SymTabEntry charId;
    public static SymTabEntry trueId;
    public static SymTabEntry falseId;


    public static void initialize(SymTabStack stack) {
        initializeTypes(stack);
        initializeConstants(stack);
    }

    private static void initializeTypes(SymTabStack stack) {
        integerId = stack.enterLocal("integer");
        integerType = TypeFactory.createType(TypeFormImpl.SCALAR);
        integerType.setIdentifier(integerId);
        integerId.setDefinition(DefinitionImpl.TYPE);
        integerId.setTypeSpec(integerType);

        realId = stack.enterLocal("real");
        realType = TypeFactory.createType(TypeFormImpl.SCALAR);
        realType.setIdentifier(realId);
        realId.setDefinition(DefinitionImpl.TYPE);
        realId.setTypeSpec(realType);

        booleanId = stack.enterLocal("boolean");
        booleanType = TypeFactory.createType(TypeFormImpl.ENUMERATION);
        booleanType.setIdentifier(booleanId);
        booleanId.setDefinition(DefinitionImpl.TYPE);
        booleanId.setTypeSpec(booleanType);

        charId = stack.enterLocal("char");
        charType = TypeFactory.createType(TypeFormImpl.SCALAR);
        charType.setIdentifier(charId);
        charId.setDefinition(DefinitionImpl.TYPE);
        charId.setTypeSpec(charType);

        undefinedType = TypeFactory.createType(TypeFormImpl.SCALAR);
    }

    private static void initializeConstants(SymTabStack stack) {
        falseId = stack.enterLocal("false");
        falseId.setDefinition(DefinitionImpl.ENUMERATION_CONSTANT);
        falseId.setTypeSpec(booleanType);
        falseId.setAttribute(SymTabKeyImpl.CONSTANT_VALUE, new Integer(0));

        trueId = stack.enterLocal("true");
        trueId.setDefinition(DefinitionImpl.ENUMERATION_CONSTANT);
        trueId.setTypeSpec(booleanType);
        trueId.setAttribute(SymTabKeyImpl.CONSTANT_VALUE, new Integer(1));

        ArrayList<SymTabEntry> constants = new ArrayList<>();
        constants.add(falseId);
        constants.add(trueId);
        booleanType.setAttribute(TypeKeyImpl.ENUMERATION_CONSTANTS, constants);
    }


}
