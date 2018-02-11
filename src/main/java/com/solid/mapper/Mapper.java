package com.solid.mapper;

import java.util.List;

import com.solid.mapping.Mapping;

/**
 * Interface for mapping an object of one type to an object of another type.
 * 
 * @author Joseph Soliday
 *
 */
public interface Mapper {
	/**
     * Maps a list of objects of one type to another type
     *
     * @param sources the source objects to map
     * @return the mapped list of objects
     * @throws MappingException thrown when an exception occurs mapping object properties
     */
	public <S, D> List<D> map(List<S> sources) throws MappingException;

    /**
     * Maps an object of one type to another type.
     *
     * @param source the source object to map
     * @param destination the destination object to map
     * @throws MappingException thrown when an exception occurs mapping object properties
     */
	public <S, D> void map(S source, D destination) throws MappingException;
    
    /**
     * Maps an object of one type to another type.
     *
     * @param source the source object to map
     * @return the mapped object
     */
	public <S, D> D map(S source);
	
	/**
	 * Gets a list of child mappers that are used for this mapper.
	 * 
	 * @return a list of children mappers
	 */
	public List<Mapper> getChildren();
	
	/**
	 * Gets a list of mappings for this mapper.
	 * 
	 * @return a list of mappings
	 */
	public List<Mapping> getMappings();
	
}
