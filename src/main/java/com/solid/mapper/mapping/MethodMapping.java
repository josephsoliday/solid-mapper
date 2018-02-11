package com.solid.mapper.mapping;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Implementation of {@link Mapping} for mapping a property.
 * 
 * @author Joseph Soliday
 * 
 */
@SuppressWarnings("rawtypes")
public class MethodMapping extends AbstractMapping<Function, BiConsumer> implements Mapping {
	
	public MethodMapping(Function source, BiConsumer destination) {
		super(source, destination, MappingType.ONE_WAY);
	}
}
