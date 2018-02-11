package com.solid.mapper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.solid.converter.Converter;
import com.solid.mapper.cache.Cache;
import com.solid.mapper.cache.CacheItem;
import com.solid.mapper.field.FieldMapper;
import com.solid.mapper.mapping.FieldMapping;
import com.solid.mapper.mapping.Mapping;
import com.solid.mapper.mapping.MappingBuilder;
import com.solid.mapper.mapping.MethodMapping;
import com.solid.mapper.method.MethodMapper;
import com.solid.mapper.property.PropertyMapper;

/**
 * Abstract class with basic functionality for implementing {@link Mapper}.
 *
 * @author Joseph Soliday
 * 
 */
public abstract class AbstractMapper<T> implements Mapper {
	
	private final List<Mapper> children = new ArrayList<Mapper>();
	
	private final Class<?> sourceType;
	private final Class<?> destinationType;
	
	private List<Mapping> mappings = null;
	
	protected AbstractMapper(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping> mappings) {
		this.sourceType = sourceType;
		this.destinationType = destinationType;
		this.mappings = mappings;
	}
	
	protected AbstractMapper(final Class<?> sourceType, final Class<?> destinationType, final MapperType type) {
		this.sourceType = sourceType;
		this.destinationType = destinationType;
		loadChildren(type);
	}
	
	protected AbstractMapper(final Class<?> sourceType, final Class<?> destinationType, final MapperType type, final List<Mapping> mappings) {
		this.sourceType = sourceType;
		this.destinationType = destinationType;
		this.mappings = mappings;
		loadChildren(type);
	}
	
	private void loadChildren(final MapperType type) {
		this.getChildren().add(type == MapperType.FIELD ? new FieldMapper(sourceType, destinationType, this.getFieldMappings()) : new PropertyMapper(sourceType, destinationType, this.getFieldMappings()));
		final List<Mapping> methodMappings = getMethodMappings();
		if (!CollectionUtils.isEmpty(methodMappings)) {
			this.getChildren().add(new MethodMapper(sourceType, destinationType, methodMappings));
		}
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
	
	protected abstract Cache<T> getCache();
	
	@Override
	public List<Mapping> getMappings() {
		if (mappings == null) {
			mappings = new ArrayList<>();
			final Annotation[] annotations = this.getClass().getAnnotationsByType(com.solid.mapper.mapping.annotation.Mapping.class);
	        if (annotations != null) {
	            for (final Annotation annotation : annotations) {
	                if (annotation instanceof com.solid.mapper.mapping.annotation.Mapping) {
	                	final com.solid.mapper.mapping.annotation.Mapping annotationMapping = (com.solid.mapper.mapping.annotation.Mapping) annotation;
	                	mappings.add(new MappingBuilder().source(annotationMapping.source())
								   						 .customSourceConverter(annotationMapping.customSourceConverter())
								   						 .sourceConverter(annotationMapping.sourceConverter())
								   						 .destination(annotationMapping.destination())
								   						 .customDestinationConverter(annotationMapping.customDestinationConverter())
								   						 .destinationConverter(annotationMapping.destinationConverter())
								   						 .type(annotationMapping.type())
								   						 .build());
	                }
	            }
	        }
		}
		return mappings;
	}
	
	private List<Mapping> getFieldMappings() {
		return getMappings().stream()
	              			.filter(m -> m instanceof FieldMapping)
	              			.collect(Collectors.toList());
	}
	
	private List<Mapping> getMethodMappings() {
		return getMappings().stream()
	              			.filter(m -> m instanceof MethodMapping)
	              			.collect(Collectors.toList());
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
		try {
			if (getCache() != null) {
				copy(source, destination);
			}
		} catch (Exception e) {
			throw new MappingException("Unable to map source to destination: " + e.getMessage(), e);
		}
		children.forEach(mapper -> mapper.map(source, destination));
	}
	
	private void copy(final Object sourceObject, 
					  final Object destinationObject) throws IllegalArgumentException, IllegalAccessException {
		copy(sourceObject, destinationObject, getCache().getItems().get(sourceObject.getClass()),
				getCache().getItems().get(destinationObject.getClass()));
	}

	private void copy(final Object sourceObject, 
					  final Object destinationObject, 
					  final List<CacheItem<T>> sourceFields,
					  final List<CacheItem<T>> destinationFields) throws IllegalArgumentException, IllegalAccessException {
		Iterator<CacheItem<T>> sourceFieldIterator = sourceFields.iterator();
		Iterator<CacheItem<T>> destinationFieldIterator = destinationFields.iterator();
		while (sourceFieldIterator.hasNext() && destinationFieldIterator.hasNext()) {
			final CacheItem<T> sourceField = sourceFieldIterator.next();
			final CacheItem<T> destinationField = destinationFieldIterator.next();
			copy(sourceField, sourceObject, getCache().getConverters().get(sourceField.getName()), destinationField,
					destinationObject);
		}
	}

	protected abstract void copy(final CacheItem<T> sourceField, final Object sourceObject, final Converter sourceConverter, final CacheItem<T> destinationField, final Object destinationObject) throws MappingException;
}
