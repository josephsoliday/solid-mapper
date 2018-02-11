package com.solid.mapper;

import java.util.List;

import com.solid.converter.Converter;
import com.solid.mapping.Mapping;

/**
 * Class for mapping objects.
 *
 * @author Joseph Soliday
 * 
 */
@SuppressWarnings("rawtypes")
public class ObjectMapper extends AbstractMapper implements Mapper {

	protected ObjectMapper(final Class<?> sourceType, final Class<?> destinationType, final MapperType type) {
		super(sourceType, destinationType, type);
	}
	
	protected ObjectMapper(final Class<?> sourceType, final Class<?> destinationType, final MapperType type, final List<Mapping<?, ?>> mappings) {
		super(sourceType, destinationType, type, mappings);
	}

	@Override
	protected MapperRules getMapperRules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void copyField(CopyItem sourceField, Object sourceObject, Converter sourceConverter,
			CopyItem destinationField, Object destinationObject) throws MappingException {
		// TODO Auto-generated method stub
		
	}
}
