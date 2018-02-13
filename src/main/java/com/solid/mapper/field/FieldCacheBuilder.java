package com.solid.mapper.field;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.solid.mapper.MappingException;
import com.solid.mapper.cache.Cache;
import com.solid.mapper.cache.CacheBuilder;
import com.solid.mapper.cache.CacheItem;
import com.solid.mapping.FieldMapping;
import com.solid.mapping.Mapping;
import com.solid.util.ReflectionUtils;

/**
 * Implementation of {@link CacheBuilder} for fields.
 * 
 * @author Joseph
 *
 */
public class FieldCacheBuilder implements CacheBuilder<Field> {

	@Override
	public Cache<Field> build(Class<?> sourceType, Class<?> destinationType, List<Mapping> mappings) {
		final FieldCache cache = new FieldCache();
		
		try {
			final List<CacheItem<Field>> sourceFields = new ArrayList<>();
			final List<CacheItem<Field>> destinationFields = new ArrayList<>();
			
			// Get a list of fields for each object
			final Map<String, Field> allSourceFields = ReflectionUtils.getFields(sourceType);
			final Map<String, Field> allDestinationFields = ReflectionUtils.getFields(destinationType);

			// Load fields from mappings
			for (final Mapping mapping : mappings) {
				final FieldMapping fieldMapping = (FieldMapping) mapping;
				if (allSourceFields.containsKey(fieldMapping.getSource())
						&& allDestinationFields.containsKey(fieldMapping.getDestination())) {
					final Field sourceField = allSourceFields.get(fieldMapping.getSource());
					final Field destinationField = allDestinationFields.get(fieldMapping.getDestination());
					sourceFields.add(new CacheItem<Field>(sourceField, sourceField.getName(), fieldMapping.getSourceConverter()));
					destinationFields.add(new CacheItem<Field>(destinationField, destinationField.getName(), fieldMapping.getDestinationConverter()));
				}
			}

			// Load same fields
			allSourceFields.entrySet().forEach(entry -> {
				if (allDestinationFields.containsKey(entry.getKey())) {
					final Field destinationField = allDestinationFields.get(entry.getKey());
					if (entry.getValue().getType().equals(destinationField.getType())) {
						sourceFields.add(new CacheItem<Field>(entry.getValue(), entry.getValue().getName()));
						destinationFields.add(new CacheItem<Field>(destinationField, destinationField.getName()));
					}
				}
			});
			
			cache.put(sourceType, sourceFields);
			cache.put(destinationType, destinationFields);
		} catch (final Exception e) {
			throw new MappingException("Unable to load fields: " + e.getMessage(), e);
		}
		
		return cache;
	}
}
