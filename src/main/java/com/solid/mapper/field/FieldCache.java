package com.solid.mapper.field;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.solid.mapper.MappingException;
import com.solid.mapper.cache.AbstractCache;
import com.solid.mapper.cache.Cache;
import com.solid.mapper.cache.CacheItem;
import com.solid.mapping.Mapping;
import com.solid.util.ReflectionUtils;

/**
 * Implementation of {@link Cache} for fields.
 * 
 * @author Joseph
 *
 */
public class FieldCache extends AbstractCache<Field> implements Cache<Field> {

	public FieldCache(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping> mappings) {
		super(sourceType, destinationType, mappings);
	}

	@Override
	protected void fill(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping> mappings) {
		if (!getItems().containsKey(sourceType)) {
			try {
				final List<CacheItem<Field>> sourceFields = new ArrayList<>();
				final List<CacheItem<Field>> destinationFields = new ArrayList<>();
				
				// Get a list of fields for each object
				final Map<String, Field> allSourceFields = ReflectionUtils.getFields(sourceType);
				final Map<String, Field> allDestinationFields = ReflectionUtils.getFields(destinationType);

				// Load fields from mappings
				for (final Mapping mapping : mappings) {
					if (allSourceFields.containsKey(mapping.getSource())
							&& allDestinationFields.containsKey(mapping.getDestination())) {
						final Field sourceField = allSourceFields.get(mapping.getSource());
						final Field destinationField = allDestinationFields.get(mapping.getDestination());
						sourceFields.add(new CacheItem<Field>(sourceField, sourceField.getName()));
						destinationFields.add(new CacheItem<Field>(destinationField, destinationField.getName()));
						if (mapping.getSourceConverter() != null) {
							getConverters().put(sourceField.getName(), mapping.getSourceConverter());
							getConverters().put(destinationField.getName(), mapping.getDestinationConverter());
						}
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
				
				getItems().put(sourceType, sourceFields);
				getItems().put(destinationType, destinationFields);
			} catch (final Exception e) {
				throw new MappingException("Unable to load fields: " + e.getMessage(), e);
			}
		}
	}
}
