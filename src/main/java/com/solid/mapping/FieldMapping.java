package com.solid.mapping;

import com.solid.converter.Converter;

/**
 * Implementation of {@link Mapping} for mapping a custom field or property.
 * 
 * @author Joseph Soliday
 * 
 */
public class FieldMapping extends AbstractMapping<String, String> implements Mapping<String, String> {

	public FieldMapping(final String source, 
						 final Converter sourceConverter, 
					     final String destination, 
						 final Converter destinationConverter, 
						 final MappingType type) {
		super(source, sourceConverter, destination, destinationConverter, type);
	}
}
