package com.solid.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Build for creating {@link Mapper} objects.
 * 
 * @author Joseph Soliday
 *
 */
public class MapperBuilder {
	private List<CustomLambdaMapping> mappings = null;
	private List<Mapper> children = new ArrayList<Mapper>();
	private MapperType type = MapperType.FIELD;

	/**
	 * Sets the mappings for this mapper.
	 * 
	 * @param mappings the mappings
	 * 
	 * @return the current instance of {@link MapperBuilder}
	 */
	public MapperBuilder setMappings(final List<CustomLambdaMapping> mappings) {
		this.mappings = mappings;
		return this;
	}
	
	/**
	 * Adds a child to the mapper.
	 * 
	 * @param mapper the child mapper
	 * 
	 * @return the current instance of {@link MapperBuilder}
	 */
	public MapperBuilder addChild(final Mapper child) {
		this.children.add(child);
		return this;
	}
	
	/**
	 * Builds a {@link Mapper}.
	 * 
	 * @param sourceType the source type to map
	 * @param destinationType the destination type to map
	 * 
	 * @return a {@link Mapper}
	 */
	public Mapper build(final Class<?> sourceType, final Class<?> destinationType) {
		Mapper mapper = new ObjectMapper(sourceType, destinationType, type);
		if (mappings != null) {
			Mapper customPropertyMapper = new CustomLambdaMapper(sourceType, destinationType);
			mapper.getChildren().add(customPropertyMapper);
		}
		if (!children.isEmpty()) {
			mapper.getChildren().addAll(children);
		}
		return mapper;
	}
	
	
}
