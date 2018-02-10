package com.solid.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.solid.mapping.Mapping;
import com.solid.mapping.MethodMapping;

/**
 * Class for mapping custom methods between objects.
 *
 * @author Joseph Soliday
 * 
 */
@SuppressWarnings("rawtypes")
public class MethodMapper extends AbstractMapper implements Mapper {
	
	private final Map<Class<?>, List<MethodMapping>> mappingCache = new HashMap<>();

	protected MethodMapper(final Class<?> sourceType, final Class<?> destinationType, final List<Mapping<?,?>> mappings) {
		super(sourceType, destinationType, mappings);
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
		final List<MethodMapping> mappings = mappingCache.get(source.getClass());
		if (mappings != null) {
			mappings.forEach(mapping -> copyProperty(source, destination, mapping.getSource(), mapping.getDestination()));
		}
	}
	
	@SuppressWarnings("unchecked")
	private <S, D> void copyProperty(S source, D destination, Function getter, BiConsumer setter) {
		setter.accept(destination, getter.apply(source));
	}

}
