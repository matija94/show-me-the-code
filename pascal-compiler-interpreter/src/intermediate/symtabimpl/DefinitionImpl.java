package intermediate.symtabimpl;

import intermediate.Definition;

public enum DefinitionImpl implements Definition {

    CONSTANT, ENUMERATION_CONSTANT("enumeration constant"),
    TYPE, VARIABLE, FIELD("record field"),
    VALUE_PARAM("value parameter"), VAR_PARAM("VAR parameter"),
    PROGRAM_PARAM("program parameter"),
    PROGRAM, PROCEDURE, FUNCTION,
    UNDEFINED;

    private String text;


    DefinitionImpl() {
        this.text = this.toString().toLowerCase();
    }

    DefinitionImpl(String text) {
        this.text = text;
    }

    @Override
    public String getText() {
        return text;
    }
}
