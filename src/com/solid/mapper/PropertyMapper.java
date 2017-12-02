package com.solid.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.solid.util.ReflectionUtils;

/**
 * Class for mapping properties between objects.
 *
 * @author Joseph Soliday
 * 
 */
class PropertyMapper extends AbstractMapper implements Mapper {
	
	private final Map<Class<?>, List<Method>> propertyCache = new HashMap<>();

	PropertyMapper(final Class<?> sourceType, final Class<?> destinationType) {
		super(sourceType, destinationType);
	}
	
	@Override
	public <S, D> void map(final S source, final D destination) throws MappingException {
		try {
			fillCache(source, destination);
			copyProperties(source, destination);
		} catch (final Exception e) {
			throw new MappingException("Error mapping the source to destination: " + e.getMessage(), e);
		}
	}
	
	private <S, D> void fillCache(final S source, final D destination) {
		if (!propertyCache.containsKey(source.getClass())) {
			try {
				final List<Method> sourceGetters = new ArrayList<>();
				final List<Method> destinationSetters = new ArrayList<>();
				loadSameProperties(source, destination, sourceGetters, destinationSetters);
				propertyCache.put(source.getClass(), sourceGetters);
				propertyCache.put(destination.getClass(), destinationSetters);
			} catch (final Exception e) {
				throw new MappingException("Unable to load properties: " + e.getMessage(), e);
			}
		}
	}
	
	private void loadSameProperties(final Object sourceObject, 
								    final Object destinationObject,
								    final List<Method> sourceGetters, 
								    final List<Method> destinationSetters) {
		// Get a list of properties for each objects and compare
		final Map<String, Method> allSourceGetters = ReflectionUtils.getGetters(sourceObject);
		final Map<String, Method> allDestinationSetters = ReflectionUtils.getSetters(destinationObject);
		allSourceGetters.entrySet().forEach(entry -> {
			if (allDestinationSetters.containsKey(entry.getKey())) {
				final Method destinationSetter = allDestinationSetters.get(entry.getKey());
				if (entry.getValue().getReturnType().equals(destinationSetter.getParameterTypes()[0])) {
					sourceGetters.add(entry.getValue());
					destinationSetters.add(destinationSetter);
				}
			}
		});
	}
	
	private void copyProperties(final Object sourceObject, 
						        final Object destinationObject) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		copyProperties(sourceObject, destinationObject, propertyCache.get(sourceObject.getClass()),
				propertyCache.get(destinationObject.getClass()));
	}

	private void copyProperties(final Object sourceObject, 
							final Object destinationObject, 
							final List<Method> sourceGetters,
							final List<Method> destinationSetters) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Iterator<Method> sourceGetterIterator = sourceGetters.iterator();
		Iterator<Method> destinationSetterIterator = destinationSetters.iterator();
		while (sourceGetterIterator.hasNext() && destinationSetterIterator.hasNext()) {
			copyProperty(sourceGetterIterator.next(), sourceObject, destinationSetterIterator.next(), destinationObject);
		}
	}

	private void copyProperty(final Method sourceGetter, 
						      final Object sourceObject, 
						      final Method destinationSetter,
						      final Object destinationObject) throws IllegalArgumentException,IllegalAccessException, InvocationTargetException {
		
		destinationSetter.invoke(destinationObject, sourceGetter.invoke(sourceObject));
		
		//final MethodHandles.Lookup lookup = MethodHandles.lookup();
	    //MethodHandle sourceGetterMH = lookup.unreflect(sourceGetter);
	    //MethodHandle destinationSetterMH = lookup.unreflect(destinationSetter);
	    //destinationSetter.invoke(destinationObject, sourceGetterMH.invoke(sourceObject));

	}
}
