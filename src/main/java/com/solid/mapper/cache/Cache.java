package com.solid.mapper.cache;

import java.util.List;
import java.util.Map;

import com.solid.converter.Converter;

/**
 * Represents a set of rules for a mapper.
 * 
 * @author Joseph
 *
 */
public interface Cache<T> {
	public Map<Class<?>, List<CacheItem<T>>> getItems();
	public Map<String, Converter> getConverters();
}
