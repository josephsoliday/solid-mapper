package com.solid.mapper.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.solid.converter.Converter;
import com.solid.mapper.mapping.Mapping;

/**
 * An abstract cache to be used to store items and converters for a mapper.
 * 
 * @author Joseph
 *
 * @param <T> the type of the items to store in the cache.
 */
public abstract class AbstractCache<T> implements Cache<T> {

	private Map<Class<?>, List<CacheItem<T>>> items = new HashMap<>();
	private final Map<String, Converter> converters = new HashMap<>();

	public AbstractCache(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping> mappings) {
		fill(sourceType, destinationType, mappings);
	}
	
	protected abstract void fill(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping> mappings);
	
	@Override
	public Map<Class<?>, List<CacheItem<T>>> getItems() {
		return items;
	}

	@Override
	public Map<String, Converter> getConverters() {
		return converters;
	}
}
