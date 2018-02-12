package com.solid.mapper.cache;

import com.solid.converter.Converter;

/**
 * Represents an item to be cached.
 * 
 * @author Joseph Soliday
 *
 */
public class CacheItem<T> {
	private T item;
	private String name;
	private Converter converter;
	
	public CacheItem(final T item, final String name) {
		this.item = item;
		this.name = name;
		this.converter = null;
	}
	
	public CacheItem(final T item, final String name, final Converter converter) {
		this.item = item;
		this.name = name;
		this.converter = converter;
	}
	
	public T getItem() {
		return item;
	}
	
	public String getName() {
		return name;
	}
	
	public Converter getConverter() {
		return converter;
	}
}
