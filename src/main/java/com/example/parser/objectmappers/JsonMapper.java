package com.example.parser.objectmappers;

import com.example.parser.annotations.ParserObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
    public ObjectReader getReader(Class<?> clazz) {
        return mapper.readerFor(clazz);
    }
}