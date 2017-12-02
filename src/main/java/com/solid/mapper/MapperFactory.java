package com.solid.mapper;

/**
 * Factory class for creating objects of type {@link Mapper}.
 * 
 * @author Joseph Soliday
 *
 */
public class MapperFactory {
	
	/**
	 * Gets a mapper.
	 * 
	 * @param sourceType the source type to map
	 * @param destinationType the destination type to map
	 * @param type the type of mapper
	 * 
	 * @return a {@link Mapper}
	 */
	public static Mapper getMapper(final Class<?> sourceType, final Class<?> destinationType, MapperType type) {
		return new ObjectMapper(sourceType, destinationType, type);
	}
}
