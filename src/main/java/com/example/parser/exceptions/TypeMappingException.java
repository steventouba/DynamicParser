package com.example.parser.exceptions;

import java.util.Collection;

public class TypeMappingException extends MappingException {

    public TypeMappingException(Collection<String> values) {
        super("Unsupported type", values);
    }
}
