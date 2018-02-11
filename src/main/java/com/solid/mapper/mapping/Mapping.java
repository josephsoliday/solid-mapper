package com.solid.mapper.mapping;

import com.solid.converter.Converter;

/**
 * Interface for defining a mapping between a source {@link S} and a destination {@link D}.
 * 
 * @author Joseph
 */
public interface Mapping {
	
	/**
	 * Gets the source mapping.
	 * 
	 * @return source mapping
	 */
	public <S> S getSource();
	
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
	public <D> D getDestination();
	
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
