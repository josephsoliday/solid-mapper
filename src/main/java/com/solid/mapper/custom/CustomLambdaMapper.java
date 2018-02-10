package com.solid.mapper.custom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.solid.mapper.AbstractMapper;
import com.solid.mapper.Mapper;
import com.solid.mapper.MappingException;
import com.solid.mapping.custom.CustomLambdaMapping;

/**
 * Class for mapping custom properties between objects.
 *
 * @author Joseph Soliday
 * 
 */
@SuppressWarnings("rawtypes")
public class CustomLambdaMapper extends AbstractMapper implements Mapper {
	
	private final Map<Class<?>, List<CustomLambdaMapping>> mappingCache = new HashMap<>();

	protected CustomLambdaMapper(final Class<?> sourceType, final Class<?> destinationType) {
		super(sourceType, destinationType);
	}
	
	@Override
	public <S, D> void map(final S source, final D destination) throws MappingException {
		try {
			copyCustomProperties(source, destination);
		} catch (final Exception e) {
			throw new MappingException("Error mapping the source to destination: " + e.getMessage(), e);
		}
	}
	
	private <S, D> void copyCustomProperties(final S source, final D destination) {
		final List<CustomLambdaMapping> mappings = mappingCache.get(source.getClass());
		if (mappings != null) {
			mappings.forEach(mapping -> copyProperty(source, destination, mapping.getSource(), mapping.getDestination()));
		}
	}
	
	@SuppressWarnings("unchecked")
	private <S, D> void copyProperty(S source, D destination, Function getter, BiConsumer setter) {
		setter.accept(destination, getter.apply(source));
	}

}
