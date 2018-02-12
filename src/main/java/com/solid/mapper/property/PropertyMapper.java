package com.solid.mapper.property;

import java.lang.reflect.Method;
import java.util.List;

import com.solid.converter.Converter;
import com.solid.mapper.AbstractMapper;
import com.solid.mapper.Mapper;
import com.solid.mapper.MappingException;
import com.solid.mapper.cache.CacheBuilder;
import com.solid.mapper.cache.CacheItem;
import com.solid.mapper.mapping.Mapping;

/**
 * Class for mapping properties between objects.
 *
 * @author Joseph Soliday
 * 
 */
public class PropertyMapper extends AbstractMapper<Method> implements Mapper {
	
	public PropertyMapper(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping> mappings) {
		super(sourceType, destinationType, mappings);
	}

	@Override
	protected CacheBuilder<Method> getCacheBuilder() {
		return new PropertyCacheBuilder();
	}

	@Override
	protected void copy(final CacheItem<Method> sourceGetter, 
						final Object sourceObject, 
						final Converter sourceConverter,
						final CacheItem<Method> destinationSetter, 
					    final Object destinationObject) throws MappingException {
		try {
			destinationSetter.getItem().invoke(destinationObject, sourceConverter != null ? sourceConverter.convert(sourceGetter.getItem().invoke(sourceObject), sourceGetter.getItem().getReturnType()) 
																						  : sourceGetter.getItem().invoke(sourceObject));
		} catch (final Exception e) {
			throw new MappingException("Unable to copy properties: " + e.getMessage(), e);
		}
		
	}
}
