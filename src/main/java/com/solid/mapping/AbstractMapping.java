package com.solid.mapping;

import com.solid.converter.Converter;

/**
 * Abstract class with basic functionality for implementing {@link Mapping}.
 * 
 * @author Joseph Soliday
 *
 */
public abstract class AbstractMapping<S,D> implements Mapping {
	
	private S source;
	private Converter sourceConverter;
	private D destination;
	private Converter destinationConverter;
	private MappingType type;
	
	public AbstractMapping(final S source, 
						   final D destination, 
						   final MappingType type) {
		this.source = source;
		this.sourceConverter = null;
		this.destination = destination;
		this.destinationConverter = null;
		this.type = type;
	}
	
	protected AbstractMapping(final S source, 
					final Converter sourceConverter, 
					final D destination, 
					final Converter destinationConverter, 
					final MappingType type) {
		this.source = source;
		this.sourceConverter = sourceConverter;
		this.destination = destination;
		this.destinationConverter = destinationConverter;
		this.type = type;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public S getSource() {
		return source;
	}
	
	@Override
	public Converter getSourceConverter() {
		return sourceConverter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public D getDestination() {
		return destination;
	}
	
	@Override
	public Converter getDestinationConverter() {
		return destinationConverter;
	}
	
	@Override
	public MappingType getType() {
		return type;
	}
}
