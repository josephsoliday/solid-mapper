package com.solid.mapper;

import com.solid.converter.Converter;
import com.solid.converter.CustomConverter;

/**
 * Builder for creating {@link Mapping} objects.
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

    public Mapping<?, ?> build() {
        return new CustomMapping(source,
                                 getConverter(customSourceConverter,
                                              sourceConverter),
                                 destination,
                                 getConverter(customDestinationConverter,
                                              destinationConverter),
                                 type);
    }

    private Converter getConverter(final String customConverter, final Class<?> converter) {
        try {
            if (customConverter != null && !customConverter.isEmpty()) {
                return new CustomConverter(customConverter);
            } else if (converter != null && !converter.equals(Converter.class)) {
                return (Converter) converter.newInstance();
            }
            return null;
        } catch (final Exception e) {
            throw new MappingException("Unable to get converter for custom mapping: " + e.getMessage(), e);
        }
    }
}
