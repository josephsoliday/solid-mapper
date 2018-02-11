package com.solid.mapper.field;

import java.lang.reflect.Field;
import java.util.List;

import com.solid.converter.Converter;
import com.solid.mapper.AbstractMapper;
import com.solid.mapper.Mapper;
import com.solid.mapper.MappingException;
import com.solid.mapper.cache.Cache;
import com.solid.mapper.cache.CacheItem;
import com.solid.mapper.mapping.Mapping;

/**
 * Class for mapping fields between objects.
 *
 * @author Joseph Soliday
 * 
 */
public class FieldMapper extends AbstractMapper<Field> implements Mapper {
	
	private final FieldCache cache;
	
	public FieldMapper(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping> mappings) {
		super(sourceType, destinationType, mappings);
		cache = new FieldCache(sourceType, destinationType, mappings);
	}

	@Override
	protected Cache<Field> getCache() {
		return cache;
	}

	@Override
	protected void copy(final CacheItem<Field> sourceField, 
					    final Object sourceObject, 
					    final Converter sourceConverter,
						final CacheItem<Field> destinationField, 
						final Object destinationObject) throws MappingException {
		try {
			sourceField.getItem().setAccessible(true);
			destinationField.getItem().setAccessible(true);
			destinationField.getItem().set(destinationObject,	sourceConverter != null ? sourceConverter.convert(sourceField.getItem().get(sourceObject), sourceField.getItem().getType())
																						: sourceField.getItem().get(sourceObject));
		} catch (final Exception e) {
			throw new MappingException("Unable to copy field: " + e.getMessage(), e);
		}
		
	}
}
