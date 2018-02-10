package com.solid.mapper;

import java.util.List;

import com.solid.mapping.Mapping;

/**
 * Class for mapping objects.
 *
 * @author Joseph Soliday
 * 
 */
public class ParentMapper extends AbstractMapper implements Mapper {
	
	protected ParentMapper(final Class<?> sourceType, final Class<?> destinationType, final MapperType type) {
		super(sourceType, destinationType, type);
	}
	
	protected ParentMapper(final Class<?> sourceType, final Class<?> destinationType, final MapperType type, final List<Mapping<?, ?>> mappings) {
		super(sourceType, destinationType, type, mappings);
	}
}
