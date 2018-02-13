package com.solid.mapper.cache;

import java.util.List;

import com.solid.mapping.Mapping;

/**
 * Interface that defines behavior for building a cache.
 * 
 * @author Joseph Soliday
 *
 */
public interface CacheBuilder<T> {
	public Cache<T> build(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping> mappings);
}
