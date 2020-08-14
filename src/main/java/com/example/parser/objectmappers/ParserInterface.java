package com.example.parser.objectmappers;

import com.example.parser.Parser;
import com.example.parser.exceptions.ParseException;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.InputStream;
import java.util.Iterator;

public interface  ParserInterface {

    public abstract ObjectWriter getWriter(Class<?> clazz);

    public abstract Iterator<?> getReader(Class<?> clazz, Parser.Properties properties, InputStream stream) throws ParseException;

}
