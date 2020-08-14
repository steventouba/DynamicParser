package com.example.parser.objectmappers;

import com.example.parser.Parser;
import com.example.parser.annotations.ParserObjectMapper;
import com.example.parser.exceptions.ParseException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@ParserObjectMapper(key = "csv")
public class CsvMapper implements ParserInterface {

    private final com.fasterxml.jackson.dataformat.csv.CsvMapper mapper;

    public CsvMapper() {
        mapper = new com.fasterxml.jackson.dataformat.csv.CsvMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE, true);

    };

    @Override
    public ObjectWriter getWriter(Class<?> clazz) {
        return mapper.writerWithTypedSchemaFor(clazz);
    }

    @Override
    public Iterator<?> getReader(Class<?> clazz, Parser.Properties properties, InputStream stream) throws ParseException {
        try {
            return mapper.readerFor(clazz).with(CsvSchema.emptySchema().withHeader()).readValues(stream);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }


}
