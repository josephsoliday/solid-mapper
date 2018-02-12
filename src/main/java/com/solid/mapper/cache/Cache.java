package com.solid.mapper.cache;

import java.util.List;
import java.util.Map;

/**
 * Represents a cache for a mapper.
 * 
 * @author Joseph Soliday
 *
 */
public interface Cache<T> extends Map<Class<?>, List<CacheItem<T>>> {
}
