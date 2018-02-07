package com.solid.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * Builder for creating {@link Mapper} objects.
 *
 * @author Joseph Soliday
 *
 */
public class MapperBuilder {
	private List<Mapping<?, ?>> mappings = new ArrayList<>();
	private List<CustomMapping> customMappings = null;
	private final List<Mapper> children = new ArrayList<>();
	private MapperType type = MapperType.FIELD;

	/**
	 * Adds a mapping.
	 *
	 * @param mapping the mapping to add.
	 * @return the current instance of {@link MapperBuilder}
	 */
    public MapperBuilder addMapping(final Mapping<?, ?> mapping) {
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
	public MapperBuilder mappings(final List<Mapping<?,?>> mappings) {
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
		final Mapper mapper = new ObjectMapper(sourceType, destinationType, type);
		if (!CollectionUtils.isEmpty(getCustomMappings())) {
			mapper.getChildren().add(new CustomFieldMapper(sourceType, destinationType, getCustomMappings()));
		}
		if (!children.isEmpty()) {
			mapper.getChildren().addAll(children);
		}
		return mapper;
	}

	private List<CustomMapping> getCustomMappings() {
		if (customMappings == null && !CollectionUtils.isEmpty(mappings)) {
			customMappings = new ArrayList<>();
			for (final Mapping<?, ?> mapping: mappings) {
				if (mapping instanceof CustomMapping) {
					customMappings.add((CustomMapping)mapping);
				}
			}
		}
		return customMappings;
	}
}
