package com.solid.mapper.property;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.solid.mapper.AbstractMapperRules;
import com.solid.mapper.CopyItem;
import com.solid.mapper.MapperRules;
import com.solid.mapper.MappingException;
import com.solid.mapping.Mapping;
import com.solid.util.ReflectionUtils;
import com.solid.util.StringUtils;

/**
 * Implementation of {@link MapperRules} for {@link Method} rules.
 * 
 * @author Joseph
 *
 */
public class PropertyMapperRules extends AbstractMapperRules<Method> implements MapperRules<Method> {

	public PropertyMapperRules(Class<?> sourceType, Class<?> destinationType, List<Mapping> mappings) {
		super(sourceType, destinationType, mappings);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void fillCache(Class<?> sourceType, Class<?> destinationType) {
		if (!getCopyItems().containsKey(sourceType)) {
			try {
				final List<CopyItem<Method>> sourceGetters = new ArrayList<>();
				final List<CopyItem<Method>> destinationSetters = new ArrayList<>();
				loadProperties(sourceType, destinationType, sourceGetters, destinationSetters);
				getCopyItems().put(sourceType, sourceGetters);
				getCopyItems().put(destinationType, destinationSetters);
			} catch (final Exception e) {
				throw new MappingException("Unable to load properties: " + e.getMessage(), e);
			}
		}
	}
	
	private void loadProperties(final Class<?> sourceType, 
								final Class<?> destinationType,
								final List<CopyItem<Method>> sourceGetters, 
								final List<CopyItem<Method>> destinationSetters) {
		// Get a list of properties for each objects
		final Map<String, Method> allSourceGetters = ReflectionUtils.getGetters(sourceType);
		final Map<String, Method> allDestinationSetters = ReflectionUtils.getSetters(destinationType);

		// Load fields from mappings
		for (final Mapping mapping : this.getMappings()) {
			if (allSourceGetters.containsKey(StringUtils.capitalize(mapping.getSource().toString()))
					&& allDestinationSetters.containsKey(StringUtils.capitalize(mapping.getDestination().toString()))) {
				final Method sourceGetter = allSourceGetters
						.get(StringUtils.capitalize(mapping.getSource().toString()));
				final Method destinationSetter = allDestinationSetters
						.get(StringUtils.capitalize(mapping.getDestination().toString()));
				sourceGetters.add(new CopyItem<Method>(sourceGetter, sourceGetter.getName()));
				destinationSetters.add(new CopyItem<Method>(destinationSetter, destinationSetter.getName()));
				if (mapping.getSourceConverter() != null) {
					getConverters().put(sourceGetter.getName(), mapping.getSourceConverter());
					getConverters().put(destinationSetter.getName(), mapping.getDestinationConverter());
				}
			}
		}

		// Load same properties
		allSourceGetters.entrySet().forEach(entry -> {
			if (allDestinationSetters.containsKey(entry.getKey())) {
				final Method destinationSetter = allDestinationSetters.get(entry.getKey());
				if (entry.getValue().getReturnType().equals(destinationSetter.getParameterTypes()[0])) {
					sourceGetters.add(new CopyItem<Method>(entry.getValue(), entry.getValue().getName()));
					destinationSetters.add(new CopyItem<Method>(destinationSetter, destinationSetter.getName()));
				}
			}
		});
	}

}
