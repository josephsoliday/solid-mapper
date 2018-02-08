package com.solid.mapper;

/**
 * Class for mapping objects.
 *
 * @author Joseph Soliday
 * 
 */
class ObjectMapper extends AbstractMapper implements Mapper {
	
	public ObjectMapper(final Class<?> sourceType, final Class<?> destinationType, MapperType type) {
		super(sourceType, destinationType, type);
	}
}
