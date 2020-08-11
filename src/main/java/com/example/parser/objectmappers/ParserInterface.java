package com.example.parser.objectmappers;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public interface ParserInterface {

    ObjectWriter getWriter(Class<?> clazz);

    ObjectReader getReader(Class<?> clazz);

}
