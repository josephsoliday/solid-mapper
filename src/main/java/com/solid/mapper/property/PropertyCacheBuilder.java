package com.solid.mapper.property;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.solid.mapper.MappingException;
import com.solid.mapper.cache.Cache;
import com.solid.mapper.cache.CacheBuilder;
import com.solid.mapper.cache.CacheItem;
import com.solid.mapping.Mapping;
import com.solid.util.ReflectionUtils;
import com.solid.util.StringUtils;

/**
 * Implementation of {@link Cache} for methods.
 * 
 * @author Joseph
 *
 */
public class PropertyCacheBuilder implements CacheBuilder<Method> {

	@Override
	public Cache<Method> build(Class<?> sourceType, Class<?> destinationType, List<Mapping> mappings) {
		final PropertyCache cache = new PropertyCache();
		
		try {
			final List<CacheItem<Method>> sourceGetters = new ArrayList<>();
			final List<CacheItem<Method>> destinationSetters = new ArrayList<>();
			
			// Get a list of properties for each objects
			final Map<String, Method> allSourceGetters = ReflectionUtils.getGetters(sourceType);
			final Map<String, Method> allDestinationSetters = ReflectionUtils.getSetters(destinationType);

			// Load fields from mappings
			for (final Mapping mapping : mappings) {
				if (allSourceGetters.containsKey(StringUtils.capitalize(mapping.getSource().toString()))
						&& allDestinationSetters.containsKey(StringUtils.capitalize(mapping.getDestination().toString()))) {
					final Method sourceGetter = allSourceGetters
							.get(StringUtils.capitalize(mapping.getSource().toString()));
					final Method destinationSetter = allDestinationSetters
							.get(StringUtils.capitalize(mapping.getDestination().toString()));
					sourceGetters.add(new CacheItem<Method>(sourceGetter, sourceGetter.getName(), mapping.getSourceConverter()));
					destinationSetters.add(new CacheItem<Method>(destinationSetter, destinationSetter.getName(), mapping.getDestinationConverter()));
				}
			}

			// Load same properties
			allSourceGetters.entrySet().forEach(entry -> {
				if (allDestinationSetters.containsKey(entry.getKey())) {
					final Method destinationSetter = allDestinationSetters.get(entry.getKey());
					if (entry.getValue().getReturnType().equals(destinationSetter.getParameterTypes()[0])) {
						sourceGetters.add(new CacheItem<Method>(entry.getValue(), entry.getValue().getName()));
						destinationSetters.add(new CacheItem<Method>(destinationSetter, destinationSetter.getName()));
					}
				}
			});
			
			cache.put(sourceType, sourceGetters);
			cache.put(destinationType, destinationSetters);
		} catch (final Exception e) {
			throw new MappingException("Unable to load properties: " + e.getMessage(), e);
		}
		
		return cache;
	}
}
