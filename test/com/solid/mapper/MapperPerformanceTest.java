package com.solid.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.solid.mapper.custom.CustomMapper;
import com.solid.mapper.custom.DtoClass;
import com.solid.mapper.custom.EntityClass;
import com.solid.mapper.custom.EntityClassSubType;

public class MapperPerformanceTest {
	
	private final CustomMapper customFieldMapper = new CustomMapper(MapperType.FIELD);
	private final CustomMapper customPropertyMapper = new CustomMapper(MapperType.PROPERTY);
	
	@Test
	public void test_map_newDto_CustomFieldEntityDtoMapper_Performance() throws Exception {
		long startTime;
		long endTime;
		long duration;
		
		// Create two distinct objects and do a copy
		final List<EntityClass> entities = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			entities.add(new EntityClass("Hello", 24, true, 33, new EntityClassSubType("test", true), 33));
		}

		// Copy objects using the mapper
		startTime = System.currentTimeMillis();
		final List<DtoClass> dtos = customFieldMapper.map(entities);
		endTime = System.currentTimeMillis();
		duration = (endTime - startTime);
		System.out.println("Performance Field Reflection: " + duration + " milliseconds");
		for(int i = 0; i < entities.size(); i++) {
			final EntityClass entity = entities.get(i);
			final DtoClass dto = dtos.get(i);
			assertEquals(entity.getString1(), dto.getString1());
			assertEquals(entity.getInteger1(), dto.getInteger1());
			assertEquals(entity.isBoolean1(), dto.isBoolean1());
			assertEquals(entity.getSize(), dto.getLength());
			assertEquals(String.valueOf(entity.getConvertValue()), dto.getConvertValue());
			//assertEquals(entity.getElement().getValue(), dto.getValue());
			//assertEquals(entity.getElement().isChecked(), dto.isChecked());
		}
	}
	
	@Test
	public void test_map_newDto_CustomPropertyEntityDtoMapper_Performance() throws Exception {
		long startTime;
		long endTime;
		long duration;
		
		// Create two distinct objects and do a copy
		final List<EntityClass> entities = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			entities.add(new EntityClass("Hello", 24, true, 33, new EntityClassSubType("test", true), 33));
		}

		// Copy objects using the mapper
		startTime = System.currentTimeMillis();
		final List<DtoClass> dtos = customPropertyMapper.map(entities);
		endTime = System.currentTimeMillis();
		duration = (endTime - startTime);
		System.out.println("Performance Property Reflection: " + duration + " milliseconds");
		for(int i = 0; i < entities.size(); i++) {
			final EntityClass entity = entities.get(i);
			final DtoClass dto = dtos.get(i);
			assertEquals(entity.getString1(), dto.getString1());
			assertEquals(entity.getInteger1(), dto.getInteger1());
			assertEquals(entity.isBoolean1(), dto.isBoolean1());
			assertEquals(entity.getSize(), dto.getLength());
			assertEquals(String.valueOf(entity.getConvertValue()), dto.getConvertValue());
			//assertEquals(entity.getElement().getValue(), dto.getValue());
			//assertEquals(entity.getElement().isChecked(), dto.isChecked());
		}
	}
	
	@Test
	public void test_map_newDto_CustomEntityDtoMapper_Performance_Manual() throws Exception {
		long startTime;
		long endTime;
		long duration;

		// Create two distinct objects and do a copy
		final List<EntityClass> entities = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			entities.add(new EntityClass("Hello", 24, true, 33, new EntityClassSubType("test", true), 33));
		}

		// Copy objects manually
		startTime = System.currentTimeMillis();
		final List<DtoClass> dtos = new ArrayList<>();
		for(int i = 0; i <entities.size(); i++) {
			final EntityClass entity = entities.get(i);
			final DtoClass dto = new DtoClass();
			dto.setString1(entity.getString1());
			dto.setInteger1(entity.getInteger1());
			dto.setBoolean1(entity.isBoolean1());
			dto.setLength(entity.getSize());
			dto.setConvertValue(String.valueOf(entity.getConvertValue()));
			//dto.setValue(entity.getElement().getValue());
			//dto.setChecked(entity.getElement().isChecked());
			dtos.add(dto);
		}
		endTime = System.currentTimeMillis();
		duration = (endTime - startTime);
		System.out.println("Performance Direct: " + duration + " milliseconds");
		for(int i = 0; i <entities.size(); i++) {
			final EntityClass entity = entities.get(i);
			final DtoClass dto = dtos.get(i);
			assertEquals(entity.getString1(), dto.getString1());
			assertEquals(entity.getInteger1(), dto.getInteger1());
			assertEquals(entity.isBoolean1(), dto.isBoolean1());
			assertEquals(entity.getSize(), dto.getLength());
			assertEquals(String.valueOf(entity.getConvertValue()), dto.getConvertValue());
			//assertEquals(entity.getElement().getValue(), dto.getValue());
			//assertEquals(entity.getElement().isChecked(), dto.isChecked());
		}
	}
}
