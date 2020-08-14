package com.example.parser.objectmappers;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public abstract class ParserInterface {

    public abstract ObjectWriter getWriter(Class<?> clazz);

    public abstract ObjectReader getReader(Class<?> clazz);

}
