package com.solid.mapper.method;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.solid.converter.Converter;
import com.solid.mapper.AbstractMapper;
import com.solid.mapper.CopyItem;
import com.solid.mapper.Mapper;
import com.solid.mapper.MapperRules;
import com.solid.mapper.MappingException;
import com.solid.mapping.Mapping;

/**
 * Class for mapping custom methods between objects.
 *
 * @author Joseph Soliday
 * 
 */
@SuppressWarnings("rawtypes")
public class MethodMapper extends AbstractMapper<FunctionalInterface> implements Mapper {
	
	public MethodMapper(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping<?,?>> mappings) {
		super(sourceType, destinationType, mappings);
	}

	@Override
	protected MapperRules<FunctionalInterface> getMapperRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void copyField(final CopyItem<FunctionalInterface> setter, 
							 final Object sourceObject, 
							 final Converter sourceConverter,
							 final CopyItem<FunctionalInterface> getter, 
							 final Object destinationObject) throws MappingException {
		((BiConsumer)setter.getItem()).accept(destinationObject, ((Function)getter.getItem()).apply(sourceObject));
	}
}
