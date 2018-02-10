package com.solid.mapping.custom;

import com.solid.converter.Converter;
import com.solid.mapping.AbstractMapping;
import com.solid.mapping.Mapping;
import com.solid.mapping.MappingType;

/**
 * Implementation of {@link Mapping} for mapping a custom field or property.
 * 
 * @author Joseph Soliday
 * 
 */
public class CustomMapping extends AbstractMapping<String, String> implements Mapping<String, String> {

	public CustomMapping(final String source, 
						 final Converter sourceConverter, 
					     final String destination, 
						 final Converter destinationConverter, 
						 final MappingType type) {
		super(source, sourceConverter, destination, destinationConverter, type);
	}
}
