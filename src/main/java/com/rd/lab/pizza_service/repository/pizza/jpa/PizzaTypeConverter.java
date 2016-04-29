package com.rd.lab.pizza_service.repository.pizza.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.domain.pizza.Pizza.Type;

@Converter
public class PizzaTypeConverter implements AttributeConverter<Pizza.Type, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Type attribute) {
		return attribute.ordinal() + 1;
	}

	@Override
	public Type convertToEntityAttribute(Integer dbData) {
		return Type.values()[dbData - 1];
	}
}