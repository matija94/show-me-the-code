package intermediate.typeimpl;

import intermediate.TypeKey;

public enum TypeKeyImpl implements TypeKey {

    // enumeration
    ENUMERATION_CONSTANTS,

    // subrange
    SUBRANGE_BASE_TYPE, SUBRANGE_MIN_VALUE, SUBRAGE_MAX_VALUE,


    // array
    ARRAY_INDEX_TYPE, ARRAY_ELEMENT_TYPE, ARRAY_ELEMENT_COUNT,

    // record
    RECORD_SYMTAB
}
