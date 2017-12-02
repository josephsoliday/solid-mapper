package com.solid.mapper.custom;

import com.solid.mapper.MapperType;
import com.solid.mapper.MappingType;
import com.solid.mapper.ObjectMapper;
import com.solid.mapper.annotation.Mapping;

@Mapping(source="size", destination="length", type=MappingType.BI_DIRECTIONAL)
@Mapping(source="convertValue", customSourceConverter="java.lang.Integer.valueOf", destination="convertValue", customDestinationConverter="java.lang.String.valueOf", type=MappingType.BI_DIRECTIONAL)
//@Mapping(source="element.value", destination="value", type=MappingType.BI_DIRECTIONAL)
//@Mapping(source="element.checked", destination="checked", type=MappingType.BI_DIRECTIONAL)
public class CustomMapper extends ObjectMapper {
	public CustomMapper(MapperType type) {
		super(EntityClass.class, DtoClass.class, type);
	}
}
