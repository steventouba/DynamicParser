package com.example.parser.exceptions;

import java.util.Collection;

public class EncodingMappingException  extends MappingException {

    public EncodingMappingException(Collection<String> values) {
        super("Unsupported encoding", values);
    }
}
