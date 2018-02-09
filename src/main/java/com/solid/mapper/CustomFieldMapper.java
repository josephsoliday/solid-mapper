package com.solid.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.solid.converter.Converter;
import com.solid.util.ObjectField;
import com.solid.util.ReflectionUtils;

/**
 * Class for mapping custom fields between objects.
 *
 * @author Joseph Soliday
 * 
 */
public class CustomFieldMapper extends AbstractMapper implements Mapper {
	
	private final Map<Class<?>, List<ObjectField>> customFieldCache = new HashMap<>();
	private final Map<String, Converter> converterCache = new HashMap<>();
	private List<CustomMapping> mappings = null;

	protected CustomFieldMapper(final Class<?> sourceType, final Class<?> destinationType, final List<CustomMapping> mappings) {
		super(sourceType, destinationType);
		this.mappings = mappings;
	}
	
	@Override
	public <S, D> void map(final S source, final D destination) throws MappingException {
		try {
			fillCache(source, destination);
			copyCustomFields(source, destination);
		} catch (final Exception e) {
			throw new MappingException("Error mapping the source to destination: " + e.getMessage(), e);
		}
	}
	
	private <S, D> void fillCache(final S source, final D destination) {
		if (!customFieldCache.containsKey(source.getClass())) {
			List<ObjectField> sourceFields = new ArrayList<>();
			List<ObjectField> destinationFields = new ArrayList<>();
			for (final CustomMapping mapping : mappings) {
                try {
                	sourceFields.add(ReflectionUtils.getObjectField(source, source.getClass().equals(this.getSourceType()) ? mapping.getSource() : mapping.getDestination()));
                	if (mapping.getSourceConverter() != null) {
                		converterCache.put(mapping.getSource(), mapping.getSourceConverter());
                	}
                	destinationFields.add(ReflectionUtils.getObjectField(destination, destination.getClass().equals(this.getDestinationType()) ? mapping.getDestination() : mapping.getSource()));
                	if (mapping.getDestinationConverter() != null) {
                		converterCache.put(mapping.getDestination(), mapping.getDestinationConverter());
                	}
                } catch (final Exception e) {
                    throw new CustomMappingException("Unable to load fields: "
                                                     + e.getMessage(),
                                                     e);
                }
            }
			customFieldCache.put(source.getClass(), sourceFields);
			customFieldCache.put(destination.getClass(), destinationFields);
		}
	}
	
	private void copyCustomFields(final Object sourceObject, 
								  final Object destinationObject) throws IllegalAccessException, 
																		 IllegalArgumentException, 
																		 InvocationTargetException, 
																		 NoSuchFieldException,
																		 SecurityException {
		copyCustomFields(sourceObject, destinationObject, customFieldCache.get(sourceObject.getClass()), customFieldCache.get(destinationObject.getClass()));
	}

	private void copyCustomFields(final Object sourceObject, 
								  final Object destinationObject,
								  final List<ObjectField> sourceFields, 
								  final List<ObjectField> destinationFields) throws IllegalAccessException, 
																					IllegalArgumentException, 
																					InvocationTargetException, 
																					NoSuchFieldException,
																					SecurityException {
		Iterator<ObjectField> sourceFieldIterator = sourceFields.iterator();
		Iterator<ObjectField> destinationFieldIterator = destinationFields.iterator();
		while (sourceFieldIterator.hasNext() && destinationFieldIterator.hasNext()) {
			ObjectField sourceField = sourceFieldIterator.next();
			ObjectField destinationField = destinationFieldIterator.next();

			Object newSourceObject = null;
			if (sourceObject.getClass().equals(sourceField.getObject().getClass())) {
				newSourceObject = sourceObject;
			} else {
				sourceField = ReflectionUtils.getObjectField(sourceObject, sourceField.getFieldName());
				newSourceObject = sourceField.getObject();
			}

			Object newDestinationObject = null;
			if (destinationObject.getClass().equals(destinationField.getObject().getClass())) {
				newDestinationObject = destinationObject;
			} else {
				destinationField = ReflectionUtils.getObjectField(destinationObject, destinationField.getFieldName());
				newDestinationObject = destinationField.getObject();
			}

			copyField(sourceField.getField(), newSourceObject, converterCache.get(sourceField.getFieldName()), destinationField.getField(), newDestinationObject);
		}
	}
	
	private void copyField(final Field sourceField, 
						   final Object sourceObject,
						   final Converter sourceConverter,
						   final Field destinationField,
						   final Object destinationObject) throws IllegalArgumentException, 
																  IllegalAccessException {
		sourceField.setAccessible(true);
		destinationField.setAccessible(true);
		destinationField.set(destinationObject, sourceConverter != null ? sourceConverter.convert(sourceField.get(sourceObject), sourceField.getType()) : sourceField.get(sourceObject));
	}
}
