package com.solid.mapper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.solid.converter.Converter;
import com.solid.converter.CustomConverter;
import com.solid.mapper.annotation.Mapping;

/**
 * Class for mapping objects.
 *
 * @author Joseph Soliday
 * 
 */
public class ObjectMapper extends AbstractMapper implements Mapper {
	
	private List<Mapping> mappings = null;
	
	public ObjectMapper(final Class<?> sourceType, final Class<?> destinationType, MapperType type) {
		super(sourceType, destinationType);
		this.getChildren().add(type == MapperType.FIELD ? new FieldMapper(sourceType, destinationType) : new PropertyMapper(sourceType, destinationType));
		if (!getMappings().isEmpty()) {
			this.getChildren().add(new CustomFieldMapper(sourceType, destinationType, convert(getMappings())));
		}
	}
	
	private List<Mapping> getMappings() {
		if (mappings == null) {
			mappings = new ArrayList<>();
			final Annotation[] annotations = this.getClass().getAnnotationsByType(Mapping.class);
	        if (annotations != null) {
	            for (final Annotation annotation : annotations) {
	                if (annotation instanceof Mapping) {
	                	mappings.add((Mapping) annotation);
	                }
	            }
	        }
		}
		return mappings;
	}
	
	private List<CustomMapping> convert(final List<Mapping> mappings) {
		final List<CustomMapping> customMappings = new ArrayList<CustomMapping>();
		mappings.forEach(mapping -> customMappings.add(new CustomMapping(mapping.source(), 
																		 getConverter(mapping.customSourceConverter(), 
																					  mapping.sourceConverter()), 
																			          mapping.destination(), 
																	     getConverter(mapping.customDestinationConverter(), 
																					  mapping.destinationConverter()), 
																			          mapping.type())));
		return customMappings;
	}
	
	private Converter getConverter(final String customConverter, final Class<?> converter) {
		try
		{
			if (customConverter != null && !customConverter.isEmpty()) {
				return new CustomConverter(customConverter);
			} else if (converter != null && !converter.equals(Converter.class)) {
				return (Converter) converter.newInstance();
			}
			return null;
		} catch (Exception e) {
			throw new CustomMappingException("Unable to get converter for custom mapping: " + e.getMessage(), e);
		}
	}
}
