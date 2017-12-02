package com.solid.mapper;

import com.solid.converter.Converter;

/**
 * Implementation of {@link Mapping} for mapping a custom field or property.
 * 
 * @author Joseph Soliday
 * 
 */
public class CustomMapping extends AbstractMapping<String, String> implements Mapping<String, String> {

	protected CustomMapping(final String source, 
						    final Converter sourceConverter, 
							final String destination, 
							final Converter destinationConverter, 
							final MappingType type) {
		super(source, sourceConverter, destination, destinationConverter, type);
	}
}
