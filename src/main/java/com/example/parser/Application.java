package com.example.parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
//        CsvMapper csvMapper = new CsvMapper();
//        csvMapper.registerModule(new Jdk8Module());
//        csvMapper.registerModule(new JavaTimeModule());
//        csvMapper.configure(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE, true);
//
//        CsvSchema salesItemSchema = CsvSchema.emptySchema().withHeader();
//        try {
//            /*encodings.get(encoding).readerFor(types.get(type) */
//            MappingIterator<SalesItem> salesItem = csvMapper
//                    .readerFor(SalesItem.class)
//                    .with(salesItemSchema)
//                    .readValues(new File("/Users/steventouba/Desktop/SalesJan2009.csv"));
//            salesItem.forEachRemaining(item -> {
//                try {
//                    System.out.println(csvMapper.writerWithTypedSchemaFor(SalesItem.class).writeValueAsString(item));
//                } catch (JsonProcessingException e) {
//                    e.printStackTrace();
//                }
//
//            });
//        } catch (Exception e) {
//            System.out.println("Error:" + e.getMessage());
//        }
        List<Object> objects = Parser.parse("json", "employee-item",
                Files.readString(Path.of("/Users/steventouba/Desktop/employees.jsonl"))
        );

        objects.forEach(System.out::println);
    }
}

/* what is the input what is the type needs to take in encoding and type, and string body contents
* Early check does type match, is accepted content and is body null or empty
* use mapping for encoding type check if type exists in map if not throw false
* use mapping for class to see if class exists
* use private final static to start and see if it makes sense
* make parse method static and constructor private
*  */
