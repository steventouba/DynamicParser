package com.example.parser.types;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public interface TypeInterface<T> {
    public TypeReference<List<T>> asTypeReference();
}
