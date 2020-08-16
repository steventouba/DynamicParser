package com.example.parser;

import com.example.parser.annotations.ParserObjectMapper;
import com.example.parser.annotations.ParserType;
import com.example.parser.exceptions.EncodingMappingException;
import com.example.parser.exceptions.ParseException;
import com.example.parser.exceptions.TypeMappingException;
import com.example.parser.objectmappers.ParserInterface;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.InputStream;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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

    private Parser() {}

//    public static List<Object> parse(String type, InputStream content) throws ParseException {
//        return parse(new Properties("json", type), content);
//    }

    public static List<Object> parse(Properties properties, InputStream stream) throws ParseException {

        return ImmutableList.copyOf(getValues(properties, stream));

    }

    public static Iterator<?> parseAsIterator(Properties properties, InputStream stream) throws ParseException {

        return getValues(properties, stream);

    }

    private static Iterator<?> getValues(Properties properties, InputStream stream) throws ParseException {

        return Optional.ofNullable(ENCODINGS.get(properties.getEncoding()))
                .orElseThrow(() -> new EncodingMappingException(ENCODINGS.keySet()))
                .getReader(getType(properties.getType()), properties, stream);

    }

    private static Class<?> getType(String type) throws ParseException {

        return Optional.ofNullable(TYPES.get(type))
                .orElseThrow(() -> new TypeMappingException(TYPES.keySet()));
    }

    @Deprecated
    private static String normalizeContent(String content) throws ParseException {
        if (content == null) { throw new ParseException("Content can't be null"); }

        if (content.isEmpty()) { throw new ParseException("Content can't be empty"); }

        return content;
    }

    public static class Properties {

        private final String encoding;

        private final String type;

        public Properties(String encoding, String type) {
            this.encoding = normalize(encoding);
            this.type = normalize(type);
        }

        public String getEncoding() {
            return encoding;
        }

        public String getType() {
            return type;
        }

        private String normalize(String string) {
            return string.trim().toLowerCase();
        }
    }
}
