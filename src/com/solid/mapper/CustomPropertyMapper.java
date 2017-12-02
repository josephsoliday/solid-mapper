package com.solid.mapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.solid.converter.Converter;
import com.solid.util.ObjectMethod;
import com.solid.util.ReflectionUtils;

/**
 * Class for mapping custom properties between objects.
 *
 * @author Joseph Soliday
 * 
 */
class CustomPropertyMapper extends AbstractMapper implements Mapper {
	private final Map<Class<?>, List<ObjectMethod>> customPropertyCache = new HashMap<>();
	private final Map<String, Converter> converterCache = new HashMap<>();
	private List<CustomMapping> mappings = null;

	CustomPropertyMapper(final Class<?> sourceType, final Class<?> destinationType, final List<CustomMapping> mappings) {
		super(sourceType, destinationType);
		this.mappings = mappings;
	}
	
	@Override
	public <S, D> void map(final S source, final D destination) throws MappingException {
		try {
			fillCache(source, destination);
			copyCustomProperties(source, destination);
		} catch (final Exception e) {
			throw new MappingException("Error mapping the source to destination: " + e.getMessage(), e);
		}
	}
	
	private <S, D> void fillCache(final S source, final D destination) {
		if (!customPropertyCache.containsKey(source.getClass())) {
			List<ObjectMethod> sourceGetters = new ArrayList<>();
			List<ObjectMethod> destinationSetters = new ArrayList<>();
			for (final CustomMapping mapping : mappings) {
                try {
                	sourceGetters.add(ReflectionUtils.getObjectMethod(source, source.getClass().equals(this.getSourceType()) ? mapping.getSource() : mapping.getDestination(), source.getClass().equals(this.getSourceType()) ? this.getSourceType() : this.getDestinationType(), true));
                	if (mapping.getSourceConverter() != null) {
                		converterCache.put(mapping.getSource(), mapping.getSourceConverter());
                	}
                	destinationSetters.add(ReflectionUtils.getObjectMethod(destination, destination.getClass().equals(this.getDestinationType()) ? mapping.getDestination() : mapping.getSource(), destination.getClass().equals(this.getDestinationType()) ? this.getDestinationType() : this.getSourceType(), false));
                	if (mapping.getDestinationConverter() != null) {
                		converterCache.put(mapping.getDestination(), mapping.getDestinationConverter());
                	}
                } catch (final Exception e) {
                    throw new CustomMappingException("Unable to load properties: "
                                                     + e.getMessage(),
                                                     e);
                }
            }
			customPropertyCache.put(source.getClass(), sourceGetters);
			customPropertyCache.put(destination.getClass(), destinationSetters);
		}
	}
	
	private void copyCustomProperties(final Object sourceObject, 
								      final Object destinationObject) throws IllegalAccessException, 
																		     IllegalArgumentException, 
																		     InvocationTargetException, 
																		     NoSuchFieldException,
																		     SecurityException, 
																		     NoSuchMethodException {
		copyCustomProperties(sourceObject, destinationObject, customPropertyCache.get(sourceObject.getClass()), customPropertyCache.get(destinationObject.getClass()));
	}

	private void copyCustomProperties(final Object sourceObject, 
								      final Object destinationObject,
								      final List<ObjectMethod> sourceGetters, 
								      final List<ObjectMethod> destinationSetters) throws IllegalAccessException, 
																					      IllegalArgumentException, 
																					      InvocationTargetException, 
																					      NoSuchFieldException,
																					      SecurityException, 
																					      NoSuchMethodException {
		Iterator<ObjectMethod> sourceGetterIterator = sourceGetters.iterator();
		Iterator<ObjectMethod> destinationSetterIterator = destinationSetters.iterator();
		while (sourceGetterIterator.hasNext() && destinationSetterIterator.hasNext()) {
			ObjectMethod sourceGetter = sourceGetterIterator.next();
			ObjectMethod destinationSetter = destinationSetterIterator.next();

			Object newSourceObject = null;
			if (sourceObject.getClass().equals(sourceGetter.getObject().getClass())) {
				newSourceObject = sourceObject;
			} else {
				sourceGetter = ReflectionUtils.getObjectMethod(sourceObject, sourceGetter.getMethodName(), sourceGetter.getType(), true);
				newSourceObject = sourceGetter.getObject();
			}

			Object newDestinationObject = null;
			if (destinationObject.getClass().equals(destinationSetter.getObject().getClass())) {
				newDestinationObject = destinationObject;
			} else {
				destinationSetter = ReflectionUtils.getObjectMethod(destinationObject, destinationSetter.getMethodName(), destinationSetter.getType(), false);
				newDestinationObject = destinationSetter.getObject();
			}

			copyProperty(sourceGetter.getMethod(), newSourceObject, converterCache.get(sourceGetter.getMethodName()), destinationSetter.getMethod(), newDestinationObject);
		}
	}
	
	private void copyProperty(final Method sourceGetter, 
						      final Object sourceObject,
						      final Converter sourceConverter,
						      final Method destinationSetter,
						      final Object destinationObject) throws IllegalArgumentException, 
																     IllegalAccessException, 
																     InvocationTargetException {
		destinationSetter.invoke(destinationObject, sourceGetter.invoke(sourceObject));
	}
}
