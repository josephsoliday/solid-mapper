package com.solid.mapper.mapping;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.solid.converter.ConverterBuilder;

/**
 * Builder for creating objects of type {@link Mapping}.
 *
 * @author Joseph Soliday
 *
 */
public class MappingBuilder {
    private String source;
    private Class<?> sourceConverter;
    private String customSourceConverter;
    private String destination;
    private Class<?> destinationConverter;
    private String customDestinationConverter;
    private MappingType type;
    
    private Function getter;
    private BiConsumer setter;

    public MappingBuilder() {

    }

    public MappingBuilder source(final String source) {
        this.source = source;
        return this;
    }

    public MappingBuilder sourceConverter(final Class<?> sourceConverter) {
        this.sourceConverter = sourceConverter;
        return this;
    }

    public MappingBuilder customSourceConverter(final String customSourceConverter) {
        this.customSourceConverter = customSourceConverter;
        return this;
    }

    public MappingBuilder destination(final String destination) {
        this.destination = destination;
        return this;
    }

    public MappingBuilder destinationConverter(final Class<?> destinationConverter) {
        this.destinationConverter = destinationConverter;
        return this;
    }

    public MappingBuilder customDestinationConverter(final String customDestinationConverter) {
        this.customDestinationConverter = customDestinationConverter;
        return this;
    }

    public MappingBuilder type(final MappingType type) {
        this.type = type;
        return this;
    }

    public Mapping build() {
        return new FieldMapping(source,
                                 new ConverterBuilder().customConverter(customSourceConverter)
                                 					   .converter(sourceConverter).build(),
                                 destination,
                                 new ConverterBuilder().customConverter(customDestinationConverter)
           					   						   .converter(destinationConverter).build(),
                                 type);
    }
}
