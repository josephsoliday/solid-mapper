package com.solid.mapper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.solid.mapper.custom.CustomFieldMapper;
import com.solid.mapping.MappingBuilder;
import com.solid.mapping.annotation.Mapping;
import com.solid.mapping.custom.CustomMapping;

/**
 * Abstract class with basic functionality for implementing {@link Mapper}.
 *
 * @author Joseph Soliday
 * 
 */
public abstract class AbstractMapper implements Mapper {
	
	private final List<Mapper> children = new ArrayList<Mapper>();
	
	private final Class<?> sourceType;
	private final Class<?> destinationType;
	
	private List<Mapping> mappings = null;
	
	protected AbstractMapper(final Class<?> sourceType, final Class<?> destinationType, MapperType type) {
		this.sourceType = sourceType;
		this.destinationType = destinationType;
		this.getChildren().add(type == MapperType.FIELD ? new FieldMapper(sourceType, destinationType) : new PropertyMapper(sourceType, destinationType));
		if (!getMappings().isEmpty()) {
			this.getChildren().add(new CustomFieldMapper(sourceType, destinationType, convert(getMappings())));
		}
	}
	
	protected AbstractMapper(final Class<?> sourceType, final Class<?> destinationType) {
		this.sourceType = sourceType;
		this.destinationType = destinationType;
	}
	
	@Override
	public List<Mapper> getChildren() {
		return children;
	}
	
	protected Class<?> getSourceType() {
		return sourceType;
	}

	protected Class<?> getDestinationType() {
		return destinationType;
	}
	
	@Override
	public List<Mapping> getMappings() {
		if (mappings == null) {
			mappings = new ArrayList<>();
			final Annotation[] annotations = this.getClass().getAnnotationsByType(Mapping.class);
	        if (annotations != null) {
	            for (final Annotation annotation : annotations) {
	                if (annotation instanceof Mapping) {
	                	mappings.add((Mapping) annotation);
	                }
	            }
	        }
		}
		return mappings;
	}
	
	private List<CustomMapping> convert(final List<Mapping> mappings) {
		final List<CustomMapping> customMappings = new ArrayList<CustomMapping>();
		mappings.forEach(mapping -> customMappings.add((CustomMapping) new MappingBuilder().source(mapping.source())
		        																		   .customSourceConverter(mapping.customSourceConverter())
		        																		   .sourceConverter(mapping.sourceConverter())
		        																		   .destination(mapping.destination())
		        																		   .customDestinationConverter(mapping.customDestinationConverter())
		        																		   .destinationConverter(mapping.destinationConverter())
		        																		   .type(mapping.type())
		        																		   .build()));
		return customMappings;
	}
	
	@Override
	public <S, D> List<D> map(final List<S> sources) throws MappingException {
		final List<D> destinations = new ArrayList<>();
        if (sources != null) {
        	sources.forEach(source -> destinations.add(map(source)));
        }
        return destinations;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <S, D> D map(final S source) {
		D destination = null;
		try {
			destination = source.getClass().equals(sourceType) ? (D)this.destinationType.newInstance() : (D)sourceType.newInstance();//(T) getType(source).newInstance();
		} catch (final Exception e) {
			throw new MappingException("Error creating instance of destination: " + e.getMessage(), e);
		}
		map(source, destination);
		return destination;
	}
	
	@Override
	public <S, D> void map(S source, D destination) throws MappingException {
		children.forEach(mapper -> mapper.map(source, destination));
	}
}
