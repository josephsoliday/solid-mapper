package com.solid.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.solid.util.ReflectionUtils;

/**
 * Class for mapping fields between objects.
 *
 * @author Joseph Soliday
 * 
 */
class FieldMapper extends AbstractMapper implements Mapper {
	
	private final Map<Class<?>, List<Field>> fieldCache = new HashMap<>();

	FieldMapper(final Class<?> sourceType, final Class<?> destinationType) {
		super(sourceType, destinationType);
	}
	
	@Override
	public <S, D> void map(final S source, final D destination) throws MappingException {
		try {
			fillCache(source, destination);
			copyFields(source, destination);
		} catch (final Exception e) {
			throw new MappingException("Error mapping the source to destination: " + e.getMessage(), e);
		}
	}
	
	private <S, D> void fillCache(final S source, final D destination) {
		if (!fieldCache.containsKey(source.getClass())) {
			try {
				final List<Field> sourceFields = new ArrayList<>();
				final List<Field> destinationFields = new ArrayList<>();
				loadSameFields(source, destination, sourceFields, destinationFields);
				fieldCache.put(source.getClass(), sourceFields);
				fieldCache.put(destination.getClass(), destinationFields);
			} catch (final Exception e) {
				throw new MappingException("Unable to load fields: " + e.getMessage(), e);
			}
		}
	}
	
	private void loadSameFields(final Object sourceObject, 
								final Object destinationObject,
								final List<Field> sourceFields, 
								final List<Field> destinationFields) {
		// Get a list of fields for each objects and compare
		final Map<String, Field> allSourceFields = ReflectionUtils.getFields(sourceObject);
		final Map<String, Field> allDestinationFields = ReflectionUtils.getFields(destinationObject);
		allSourceFields.entrySet().forEach(entry -> {
			if (allDestinationFields.containsKey(entry.getKey())) {
				final Field destinationField = allDestinationFields.get(entry.getKey());
				if (entry.getValue().getType().equals(destinationField.getType())) {
					sourceFields.add(entry.getValue());
					destinationFields.add(destinationField);
				}
			}
		});
	}
	
	private void copyFields(final Object sourceObject, 
						    final Object destinationObject) throws IllegalArgumentException, IllegalAccessException {
		copyFields(sourceObject, destinationObject, fieldCache.get(sourceObject.getClass()),
				fieldCache.get(destinationObject.getClass()));
	}

	private void copyFields(final Object sourceObject, 
							final Object destinationObject, 
							final List<Field> sourceFields,
							final List<Field> destinationFields) throws IllegalArgumentException, IllegalAccessException {
		Iterator<Field> sourceFieldIterator = sourceFields.iterator();
		Iterator<Field> destinationFieldIterator = destinationFields.iterator();
		while (sourceFieldIterator.hasNext() && destinationFieldIterator.hasNext()) {
			copyField(sourceFieldIterator.next(), sourceObject, destinationFieldIterator.next(), destinationObject);
		}
	}

	private void copyField(final Field sourceField, 
						final Object sourceObject, 
						final Field destinationField,
						final Object destinationObject) throws IllegalArgumentException, IllegalAccessException {
		sourceField.setAccessible(true);
		destinationField.setAccessible(true);
		destinationField.set(destinationObject, sourceField.get(sourceObject));
	}
}
