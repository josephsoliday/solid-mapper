package com.solid.mapper.field;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.solid.mapper.AbstractMapperRules;
import com.solid.mapper.CopyItem;
import com.solid.mapper.MapperRules;
import com.solid.mapper.MappingException;
import com.solid.mapping.Mapping;
import com.solid.util.ReflectionUtils;

/**
 * Implementation of {@link MapperRules} for {@link Field} rules.
 * 
 * @author Joseph
 *
 */
public class FieldMapperRules extends AbstractMapperRules<Field> implements MapperRules<Field> {

	public FieldMapperRules(Class<?> sourceType, Class<?> destinationType, final List<Mapping<?, ?>> mappings) {
		super(sourceType, destinationType, mappings);
	}

	@Override
	protected void fillCache(Class<?> sourceType, Class<?> destinationType) {
		if (!getCopyItems().containsKey(sourceType)) {
			try {
				final List<CopyItem<Field>> sourceFields = new ArrayList<>();
				final List<CopyItem<Field>> destinationFields = new ArrayList<>();
				loadFields(sourceType, destinationType, sourceFields, destinationFields);
				getCopyItems().put(sourceType, sourceFields);
				getCopyItems().put(destinationType, destinationFields);
			} catch (final Exception e) {
				throw new MappingException("Unable to load fields: " + e.getMessage(), e);
			}
		}
	}
	
	private void loadFields(final Class<?> sourceType, 
							final Class<?> destinationType, 
							final List<CopyItem<Field>> sourceFields,
							final List<CopyItem<Field>> destinationFields) {
		// Get a list of fields for each object
		final Map<String, Field> allSourceFields = ReflectionUtils.getFields(sourceType);
		final Map<String, Field> allDestinationFields = ReflectionUtils.getFields(destinationType);

		// Load fields from mappings
		for (final Mapping<?, ?> mapping : this.getMappings()) {
			if (allSourceFields.containsKey(mapping.getSource())
					&& allDestinationFields.containsKey(mapping.getDestination())) {
				final Field sourceField = allSourceFields.get(mapping.getSource());
				final Field destinationField = allDestinationFields.get(mapping.getDestination());
				sourceFields.add(new CopyItem<Field>(sourceField, sourceField.getName()));
				destinationFields.add(new CopyItem<Field>(destinationField, destinationField.getName()));
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
					sourceFields.add(new CopyItem<Field>(entry.getValue(), entry.getValue().getName()));
					destinationFields.add(new CopyItem<Field>(destinationField, destinationField.getName()));
				}
			}
		});
	}
}
