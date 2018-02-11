package com.solid.mapper;

/**
 * Represents an item to be copied.
 * 
 * @author Joseph
 *
 */
public class CopyItem<T> {
	private T item;
	private String name;
	
	public CopyItem(final T item, final String name) {
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
