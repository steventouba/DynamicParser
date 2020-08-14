package com.example.parser;

import com.example.parser.annotations.ParserObjectMapper;
import com.example.parser.annotations.ParserType;
import com.example.parser.exceptions.EncodingMappingException;
import com.example.parser.exceptions.ParseException;
import com.example.parser.exceptions.TypeMappingException;
import com.example.parser.objectmappers.ParserInterface;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.collect.ImmutableMap;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Scanner;

public class Parser {

    private static final Reflections REFLECTIONS = new Reflections(
            new ConfigurationBuilder()
                    .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner())
                    .setUrls(ClasspathHelper.forJavaClassPath())
    );

    private static final ImmutableMap<String, ParserInterface> ENCODINGS = REFLECTIONS.getTypesAnnotatedWith(ParserObjectMapper.class).stream()
            .map(clazz -> {
                try {
                    return new AbstractMap.SimpleImmutableEntry<>(
                            clazz.getAnnotation(ParserObjectMapper.class).key(),
                            (ParserInterface) clazz.getConstructor().newInstance()
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(ImmutableMap.toImmutableMap(
                    AbstractMap.SimpleImmutableEntry::getKey,
                    AbstractMap.SimpleImmutableEntry::getValue
            ));

    private static final ImmutableMap<String, Class<?>> TYPES = REFLECTIONS.getTypesAnnotatedWith(ParserType.class).stream()
            .map(clazz -> {
                try {
                    return new AbstractMap.SimpleImmutableEntry<>(
                            clazz.getAnnotation(ParserType.class).key(),
                            clazz
                    );
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(ImmutableMap.toImmutableMap(
                    AbstractMap.SimpleImmutableEntry::getKey,
                    AbstractMap.SimpleImmutableEntry::getValue
            ));

    private Parser() { }

    public static List<Object> parse(String encoding, String type, String content) throws ParseException, FileNotFoundException {
        Class<?> clazz = getType(type);
        ObjectReader parser = getParser(encoding, clazz);
        String normalizedContent = normalizeContent(content);
        Scanner scanner = new Scanner(content);

        try {
            return  parser.readValues(normalizedContent).readAll();
        } catch (IOException e) {
           throw new ParseException(e.getMessage(), e);
        }
    }

    private static ObjectReader getParser(String encoding, Class<?> clazz) throws ParseException {

        encoding = encoding.toLowerCase().trim();

        if (ENCODINGS.containsKey(encoding)) {
            return ENCODINGS.get(encoding).getReader(clazz);
        }

        throw new EncodingMappingException(ENCODINGS.keySet());
    }

    private static Class<?> getType(String type) throws ParseException {

        type = type.toLowerCase().trim();

        if (TYPES.containsKey(type)) {
            return TYPES.get(type);
        }

        throw new TypeMappingException(TYPES.keySet());
    }

    private static String normalizeContent(String content) throws ParseException {
        if (content == null) { throw new ParseException("Content can't be null"); }

        if (content.isEmpty()) { throw new ParseException("Content can't be empty"); }

        return content;
    }
}
