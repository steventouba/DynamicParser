package com.example.parser.exceptions;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public abstract class MappingException extends ParseException {

    protected final Collection<?> values;

    protected MappingException(String message, Collection<?> values) {
        super(message);
        this.values = values;
    }

    public final Collection<?> getValues() {
        return values;
    };

    @Override
    public String toString() {
        return String.format("%s, must be one of the following: %s", getMessage(), StringUtils.join(values, ", "));
    }
}
