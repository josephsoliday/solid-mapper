package com.solid.mapper;

import java.util.List;
import java.util.Map;

import com.solid.converter.Converter;

/**
 * Represents a set of rules for a mapper.
 * 
 * @author Joseph
 *
 */
public interface MapperRules<T> {
	public Map<Class<?>, List<CopyItem<T>>> getCopyItems();
	public Map<String, Converter> getConverters();
}
