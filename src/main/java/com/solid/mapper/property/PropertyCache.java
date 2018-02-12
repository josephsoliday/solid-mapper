package com.solid.mapper.property;

import java.lang.reflect.Method;

import com.solid.mapper.cache.AbstractCache;
import com.solid.mapper.cache.Cache;

/**
 * Implementation of {@link Cache} for properties.
 * 
 * @author Joseph Soliday
 *
 */
public class PropertyCache extends AbstractCache<Method> implements Cache<Method> {
	private static final long serialVersionUID = 1L;
}
