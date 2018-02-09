package com.solid.mapper;

/**
 * Class for mapping objects.
 *
 * @author Joseph Soliday
 * 
 */
public class ParentMapper extends AbstractMapper implements Mapper {
	
	protected ParentMapper(final Class<?> sourceType, final Class<?> destinationType, MapperType type) {
		super(sourceType, destinationType, type);
	}
}
