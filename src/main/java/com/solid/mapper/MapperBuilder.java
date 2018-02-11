package com.solid.mapper;

import java.util.ArrayList;
import java.util.List;

import com.solid.mapper.mapping.Mapping;

/**
 * Builder for creating objects of type {@link Mapper}.
 *
 * @author Joseph Soliday
 *
 */
public class MapperBuilder {
	private List<Mapping> mappings = new ArrayList<>();
	private final List<Mapper> children = new ArrayList<>();
	private MapperType type = MapperType.FIELD;

	/**
	 * Adds a mapping.
	 *
	 * @param mapping the mapping to add.
	 * @return the current instance of {@link MapperBuilder}
	 */
    public MapperBuilder addMapping(final Mapping mapping) {
		mappings.add(mapping);
		return this;
	}

	/**
	 * Sets the mappings for this mapper.
	 *
	 * @param mappings the mappings
	 *
	 * @return the current instance of {@link MapperBuilder}
	 */
	public MapperBuilder mappings(final List<Mapping> mappings) {
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
		children.add(child);
		return this;
	}

	/**
	 * Sets the mapper type.
	 *
	 * @param type the mapper type
	 *
	 * @return the current instance of {@link MapperBuilder}
	 */
	public MapperBuilder type(final MapperType type) {
		this.type = type;
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
		return new ObjectMapper(sourceType, destinationType, type, mappings);
	}
}
