package com.solid.mapper.field;

import java.lang.reflect.Field;

import com.solid.mapper.cache.AbstractCache;
import com.solid.mapper.cache.Cache;

/**
 * Implementation of {@link Cache} for fields.
 * 
 * @author Joseph Soliday
 *
 */
public class FieldCache extends AbstractCache<Field> implements Cache<Field> {
	private static final long serialVersionUID = 1L;
}
