package com.example.parser.objectmappers;

import com.example.parser.Parser;
import com.example.parser.annotations.ParserObjectMapper;
import com.example.parser.exceptions.ParseException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@ParserObjectMapper(key = "xml")
public class XmlMapper implements ParserInterface {

    private final com.fasterxml.jackson.dataformat.xml.XmlMapper mapper;

    public XmlMapper() {
        mapper = new com.fasterxml.jackson.dataformat.xml.XmlMapper();
        mapper.registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
    }

    @Override
    public ObjectWriter getWriter(Class<?> clazz) {
        return mapper.writer();
    }

    @Override
    public Iterator<?> getReader(Class<?> clazz, Parser.Properties properties, InputStream stream) throws ParseException {
        try {
            return mapper.getFactory().createParser(stream).readValuesAs(clazz);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }
}
