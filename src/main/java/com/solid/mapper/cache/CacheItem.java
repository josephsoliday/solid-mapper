package com.solid.mapper.cache;

/**
 * Represents an item to be copied.
 * 
 * @author Joseph
 *
 */
public class CacheItem<T> {
	private T item;
	private String name;
	
	public CacheItem(final T item, final String name) {
		this.item = item;
		this.name = name;
	}
	
	public T getItem() {
		return item;
	}
	public String getName() {
		return name;
	}
}
