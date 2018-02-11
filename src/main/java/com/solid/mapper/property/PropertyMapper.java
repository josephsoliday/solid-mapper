package com.solid.mapper.property;

import java.lang.reflect.Method;
import java.util.List;

import com.solid.converter.Converter;
import com.solid.mapper.AbstractMapper;
import com.solid.mapper.CopyItem;
import com.solid.mapper.Mapper;
import com.solid.mapper.MapperRules;
import com.solid.mapper.MappingException;
import com.solid.mapping.Mapping;

/**
 * Class for mapping properties between objects.
 *
 * @author Joseph Soliday
 * 
 */
public class PropertyMapper extends AbstractMapper<Method> implements Mapper {

	private final PropertyMapperRules mapperRules;
	
	public PropertyMapper(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping> mappings) {
		super(sourceType, destinationType, mappings);
		mapperRules = new PropertyMapperRules(sourceType, destinationType, mappings);
	}

	@Override
	protected MapperRules<Method> getMapperRules() {
		return mapperRules;
	}

	@Override
	protected void copyField(final CopyItem<Method> sourceGetter, 
							 final Object sourceObject, 
							 final Converter sourceConverter,
							 final CopyItem<Method> destinationSetter, 
							 final Object destinationObject) throws MappingException {
		try {
			destinationSetter.getItem().invoke(destinationObject, sourceConverter != null ? sourceConverter.convert(sourceGetter.getItem().invoke(sourceObject), sourceGetter.getItem().getReturnType()) 
					: sourceGetter.getItem().invoke(sourceObject));
		} catch (final Exception e) {
			throw new MappingException("Unable to copy properties: " + e.getMessage(), e);
		}
		
	}
}
