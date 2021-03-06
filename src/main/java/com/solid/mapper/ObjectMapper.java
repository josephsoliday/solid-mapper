package com.solid.mapper;

import java.util.List;

import com.solid.converter.Converter;
import com.solid.mapper.cache.CacheBuilder;
import com.solid.mapper.cache.CacheItem;
import com.solid.mapping.Mapping;

/**
 * Class for mapping objects.
 *
 * @author Joseph Soliday
 * 
 */
@SuppressWarnings("rawtypes")
public class ObjectMapper extends AbstractMapper implements Mapper {

	@SuppressWarnings("unchecked")
	protected ObjectMapper(final Class<?> sourceType, final Class<?> destinationType, final MapperType type) {
		super(sourceType, destinationType, type);
	}
	
	@SuppressWarnings("unchecked")
	protected ObjectMapper(final Class<?> sourceType, final Class<?> destinationType, final MapperType type, final List<Mapping> mappings) {
		super(sourceType, destinationType, type, mappings);
	}
	
	@Override
	protected CacheBuilder getCacheBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void copy(CacheItem sourceField, Object sourceObject, Converter sourceConverter,
			CacheItem destinationField, Object destinationObject) throws MappingRuntimeException {
		// TODO Auto-generated method stub
		
	}
}
