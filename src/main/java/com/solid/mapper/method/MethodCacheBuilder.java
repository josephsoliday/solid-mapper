package com.solid.mapper.method;
import java.util.ArrayList;
import java.util.List;

import com.solid.mapper.cache.Cache;
import com.solid.mapper.cache.CacheBuilder;
import com.solid.mapper.cache.CacheItem;
import com.solid.mapping.Mapping;
import com.solid.mapping.MethodMapping;

/**
 * Implementation of {@link Cache} for methods.
 * 
 * @author Joseph Soliday
 *
 */
public class MethodCacheBuilder implements CacheBuilder<FunctionalInterface> {

	@Override
	public Cache<FunctionalInterface> build(Class<?> sourceType, Class<?> destinationType, List<Mapping> mappings) {
		final MethodCache cache = new MethodCache();
		
		final List<CacheItem<FunctionalInterface>> sourceGetters = new ArrayList<>();
		final List<CacheItem<FunctionalInterface>> destinationSetters = new ArrayList<>();
		
		// Load fields from mappings
		mappings.forEach(mapping -> {
			final MethodMapping methodMapping = (MethodMapping) mapping;
			sourceGetters.add(new CacheItem<FunctionalInterface>((FunctionalInterface) methodMapping.getSource(), null, methodMapping.getSourceConverter()));
			destinationSetters.add(new CacheItem<FunctionalInterface>((FunctionalInterface) methodMapping.getDestination(), null, methodMapping.getDestinationConverter()));
		});
		
		cache.put(sourceType, sourceGetters);
		cache.put(destinationType, destinationSetters);
		
		return cache;
	}
}
