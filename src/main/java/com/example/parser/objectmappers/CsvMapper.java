package com.example.parser.objectmappers;

import com.example.parser.annotations.ParserObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
    public ObjectReader getReader(Class<?> clazz) {

        return mapper.readerFor(clazz).with(CsvSchema.emptySchema().withHeader());
    }


}
