package com.solid.mapper;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.junit.Test;

import com.solid.mapper.custom.BaseDto;
import com.solid.mapper.custom.CustomMapper;
import com.solid.mapper.custom.DtoClass;
import com.solid.mapper.custom.EntityClass;
import com.solid.mapping.MappingType;
import com.solid.mapping.custom.CustomLambdaMapping;

public class MapperTest {
	
	private final Mapper mapper = new ParentMapper(EntityClass.class, DtoClass.class, MapperType.FIELD);
	private final CustomMapper customMapper = new CustomMapper(MapperType.FIELD);
	
	@SuppressWarnings("rawtypes")
	@Test
	public void test_map_withMappings() throws Exception {
		//Mappings mappings = new MappingsBuilder().add(TestEvent::getSourceId, TestEvent::setId).build();
		TestEvent testEvent = new TestEvent();
		
		call2(testEvent, testEvent, TestEvent::getSourceId, TestEvent::setId);
		
		
		Function<TestEvent, Long> getter = TestEvent::getSourceId;
		BiConsumer<TestEvent, Long> setter = TestEvent::setId;
		CustomLambdaMapping lambdaMapping = new CustomLambdaMapping(getter, setter, MappingType.BI_DIRECTIONAL);
		lambdaMapping = new CustomLambdaMapping(getter, setter, MappingType.BI_DIRECTIONAL);
		
		call(testEvent, testEvent, getter, setter);
		
		
		
		//for (PropertyMapping mapping: mappings.getItems()) {
		//	call(testEvent, testEvent, mapping.getGetter(), mapping.getSetter());
		//}
		
		// String.class here is the parameter type, that might not be the case with you
		Integer convertValue = new Integer(33);
		boolean isPrimitive = convertValue.getClass().isPrimitive();
		Class<?> clazz = Class.forName("java.lang.String");
		Method[] methods = clazz.getDeclaredMethods();
		//Method method = clazz.getDeclaredMethod("valueOf", Integer.class);
		//Object o = method.invoke(null, convertValue);

	}
	
	@Test
	public void test_map_EntityToDto() throws Exception {
		long startTime;
		long endTime;
		long duration;

		// Create two distinct objects and do a copy
		EntityClass entity = new EntityClass("Hello", 24, true);
		DtoClass dto = new DtoClass(null, null, false);

		// Copy objects using the mapper
		startTime = System.currentTimeMillis();
		mapper.map(entity, dto);
		endTime = System.currentTimeMillis();
		duration = (endTime - startTime);
		System.out.println("Reflection: " + duration + " milliseconds");
		assertEquals(entity.getString1(), dto.getString1());
		assertEquals(entity.getInteger1(), dto.getInteger1());
		assertEquals(entity.isBoolean1(), dto.isBoolean1());
	}
	
	@Test
	public void test_map_EntityToDto_newDto() throws Exception {
		long startTime;
		long endTime;
		long duration;

		// Create two distinct objects and do a copy
		EntityClass entity = new EntityClass("Hello", 24, true);

		// Copy objects using the mapper
		startTime = System.currentTimeMillis();
		DtoClass dto = mapper.map(entity);
		endTime = System.currentTimeMillis();
		duration = (endTime - startTime);
		System.out.println("Reflection: " + duration + " milliseconds");
		assertEquals(entity.getString1(), dto.getString1());
		assertEquals(entity.getInteger1(), dto.getInteger1());
		assertEquals(entity.isBoolean1(), dto.isBoolean1());
	}
	
	@Test
	public void test_map_DtoToEntity() throws Exception {
		long startTime;
		long endTime;
		long duration;

		// Create two distinct objects and do a copy
		final EntityClass entity = new EntityClass(null, null, false);
		final DtoClass dto = new DtoClass("Hello", 24, true);

		// Copy objects using the mapper
		startTime = System.currentTimeMillis();
		mapper.map(dto, entity);
		endTime = System.currentTimeMillis();
		duration = (endTime - startTime);
		System.out.println("Reflection: " + duration + " milliseconds");
		assertEquals(entity.getString1(), dto.getString1());
		assertEquals(entity.getInteger1(), dto.getInteger1());
		assertEquals(entity.isBoolean1(), dto.isBoolean1());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <D, E, T> void call(D source, E dest, Function getter, BiConsumer setter) {
		setter.accept(dest, getter.apply(source));
	}
	
	public <S, D, T> void call2(S source, D dest, Function<S, T> getter, BiConsumer<D, T> setter) {
		setter.accept(dest, getter.apply(source));
	}
	
	private class TestEvent {
		private Long id;
		private BaseDto dto;
		
		public Long getSourceId() {
			return Long.valueOf(33);
		}
		
	    public void setId(Long id) {
	    	this.id = id;
	    }
	    
	    public void setDto(BaseDto dto) {
	    	this.dto = dto;
	    }
	}
}
