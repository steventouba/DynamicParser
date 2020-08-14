package com.example.parser.objectmappers;

import com.example.parser.annotations.ParserObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ParserObjectMapper(key = "xml")
public class XmlMapper extends ParserInterface {

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
    public ObjectReader getReader(Class<?> clazz) {
        return mapper.readerFor(clazz);
    }
}
