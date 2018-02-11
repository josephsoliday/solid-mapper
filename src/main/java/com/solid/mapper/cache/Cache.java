package com.solid.mapper.cache;

import java.util.List;
import java.util.Map;

import com.solid.converter.Converter;

/**
 * Represents a cache for a mapper.
 * 
 * @author Joseph Soliday
 *
 */
public interface Cache<T> {
	public Map<Class<?>, List<CacheItem<T>>> getItems();
	public Map<String, Converter> getConverters();
}
