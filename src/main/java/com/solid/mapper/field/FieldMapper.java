package com.solid.mapper.field;

import java.lang.reflect.Field;
import java.util.List;

import com.solid.converter.Converter;
import com.solid.mapper.AbstractMapper;
import com.solid.mapper.CopyItem;
import com.solid.mapper.Mapper;
import com.solid.mapper.MapperRules;
import com.solid.mapper.MappingException;
import com.solid.mapping.Mapping;

/**
 * Class for mapping fields between objects.
 *
 * @author Joseph Soliday
 * 
 */
public class FieldMapper extends AbstractMapper<Field> implements Mapper {
	
	private final FieldMapperRules mapperRules;
	
	public FieldMapper(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping<?,?>> mappings) {
		super(sourceType, destinationType, mappings);
		mapperRules = new FieldMapperRules(sourceType, destinationType, mappings);
	}

	@Override
	protected MapperRules<Field> getMapperRules() {
		return mapperRules;
	}

	@Override
	protected void copyField(final CopyItem<Field> sourceField, 
							 final Object sourceObject, 
							 final Converter sourceConverter,
							 final CopyItem<Field> destinationField, 
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
