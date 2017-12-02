package com.solid.mapper;

import com.solid.converter.Converter;

/**
 * Interface for defining a mapping between a source {@link S} and a destination {@link D}.
 * 
 * @author Joseph
 * 
 * @param <S> the source type
 * @param <D> the destination type
 *
 */
public interface Mapping<S,D> {
	
	/**
	 * Gets the source mapping.
	 * 
	 * @return source mapping
	 */
	public S getSource();
	
	/**
	 * Gets the converter to use for mapping the source
	 * 
	 * @return the source converter
	 */
	public Converter getSourceConverter();
	
	/**
	 * Gets the destination mapping.
	 * 
	 * @return the destination mapping
	 */
	public D getDestination();
	
	/**
	 * Gets the converter to use for mapping the destination
	 * 
	 * @return the destination converter
	 */
	public Converter getDestinationConverter();
	
	/**
	 * Gets the mapping type.
	 * 
	 * @return a {@link MappingType}
	 */
	MappingType getType();
}
