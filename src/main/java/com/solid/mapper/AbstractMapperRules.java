package com.solid.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.solid.converter.Converter;
import com.solid.mapping.Mapping;

/**
 * An abstract cache to be used as a base for storing copy items and converters.
 * 
 * @author Joseph
 *
 * @param <T> the type of the copy items to store in the cache.
 */
public abstract class AbstractMapperRules<T> implements MapperRules<T> {

	private Map<Class<?>, List<CopyItem<T>>> copyItems = new HashMap<>();
	private final Map<String, Converter> converters = new HashMap<>();
	private List<Mapping> mappings = new ArrayList<>();

	public AbstractMapperRules(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping> mappings) {
		this.mappings = mappings;
		fillCache(sourceType, destinationType);
	}
	
	protected abstract void fillCache(final Class<?> sourceType, final Class<?> destinationType);
	
	@Override
	public Map<Class<?>, List<CopyItem<T>>> getCopyItems() {
		return copyItems;
	}

	@Override
	public Map<String, Converter> getConverters() {
		return converters;
	}
	
	protected List<Mapping> getMappings() {
		return this.mappings;
	}

}
