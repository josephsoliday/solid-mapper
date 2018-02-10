package com.solid.mapping.custom;

import java.util.function.BiConsumer;
import java.util.function.Function;

import com.solid.mapping.AbstractMapping;
import com.solid.mapping.Mapping;
import com.solid.mapping.MappingType;

/**
 * Implementation of {@link Mapping} for mapping a property.
 * 
 * @author Joseph Soliday
 * 
 */
@SuppressWarnings("rawtypes")
public class CustomMethodMapping extends AbstractMapping<Function, BiConsumer> implements Mapping<Function, BiConsumer> {
	
	public CustomMethodMapping(Function source, BiConsumer destination) {
		super(source, destination, MappingType.ONE_WAY);
	}
}
