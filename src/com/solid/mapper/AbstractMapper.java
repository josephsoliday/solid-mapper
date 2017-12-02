package com.solid.mapper;

import java.util.ArrayList;
import java.util.List;

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
	
	AbstractMapper(final Class<?> sourceType, final Class<?> destinationType) {
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
