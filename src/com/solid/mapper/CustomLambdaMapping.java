package com.solid.mapper;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Implementation of {@link Mapping} for mapping a property.
 * 
 * @author Joseph Soliday
 * 
 */
@SuppressWarnings("rawtypes")
public class CustomLambdaMapping extends AbstractMapping<Function, BiConsumer> implements Mapping<Function, BiConsumer> {
	
	protected CustomLambdaMapping(Function source, BiConsumer destination, final MappingType type) {
		super(source, destination, type);
	}
}
