package intermediate.typeimpl;

import intermediate.SymTabEntry;
import intermediate.TypeForm;
import intermediate.TypeKey;
import intermediate.TypeSpec;

import java.util.HashMap;


public class TypeSpecImpl extends HashMap<TypeKey, Object> implements TypeSpec {

    private TypeForm form;

    private SymTabEntry identifier;

    public TypeSpecImpl(TypeForm form) {
        this.form = form;
        this.identifier = null;
    }

    public TypeSpecImpl(String value) {
        this.form = TypeFormImpl.ARRAY;

        TypeSpec indexType = new TypeSpecImpl(TypeFormImpl.SUBRANGE);
        indexType.setAttribute(TypeKeyImpl.SUBRANGE_MIN_VALUE, 1);
        indexType.setAttribute(TypeKeyImpl.SUBRAGE_MAX_VALUE, value.length());

        setAttribute(TypeKeyImpl.ARRAY_INDEX_TYPE, indexType);
        setAttribute(TypeKeyImpl.ARRAY_ELEMENT_TYPE, Predefined.charType);
        setAttribute(TypeKeyImpl.ARRAY_ELEMENT_COUNT, value.length());
    }

    @Override
    public TypeForm getForm() {
        return form;
    }

    @Override
    public void setIdentifier(SymTabEntry identifier) {
        this.identifier = identifier;
    }

    @Override
    public SymTabEntry getIdentifier() {
        return identifier;
    }

    @Override
    public void setAttribute(TypeKey key, Object value) {
        put(key, value);
    }

    @Override
    public Object getAttribute(TypeKey key) {
        return get(key);
    }

    @Override
    public boolean isPascalString() {
        if (form == TypeFormImpl.ARRAY) {
            TypeSpec elementType = (TypeSpec) getAttribute(TypeKeyImpl.ARRAY_ELEMENT_TYPE);
            TypeSpec indexType = (TypeSpec) getAttribute(TypeKeyImpl.ARRAY_INDEX_TYPE);

            return (elementType.baseType() == Predefined.charType) &&
                    (indexType.baseType() == Predefined.integerType);
        }
        return false;
    }

    @Override
    public TypeSpec baseType() {
        return (TypeSpecImpl) (form == TypeFormImpl.SUBRANGE ?
                (TypeSpec) getAttribute(TypeKeyImpl.SUBRANGE_BASE_TYPE) :
                this);
    }
}
