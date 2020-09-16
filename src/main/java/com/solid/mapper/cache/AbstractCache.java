package com.solid.mapper.cache;

import java.util.HashMap;
import java.util.List;

/**
 * An abstract cache to be used to store items for a mapper.
 * 
 * @author Joseph Soliday
 *
 * @param <T> the type of the items to store in the cache.
 */
public abstract class AbstractCache<T> extends HashMap<Class<?>, List<CacheItem<T>>> implements Cache<T> {
	private static final long serialVersionUID = 1L;
}
