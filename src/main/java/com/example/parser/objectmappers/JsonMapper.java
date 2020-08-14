package com.example.parser.objectmappers;

import com.example.parser.Parser;
import com.example.parser.annotations.ParserObjectMapper;
import com.example.parser.exceptions.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@ParserObjectMapper(key = "json")
public class JsonMapper implements ParserInterface {

    private final ObjectMapper mapper;

    public JsonMapper() {
        mapper = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    };

    @Override
    public ObjectWriter getWriter(Class<?> clazz) {
        return  mapper.writer();
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
