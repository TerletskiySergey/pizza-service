package com.rd.lab.pizza_service.infrastructure.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;

public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String arg) {
		return new BigDecimal(arg);
	}
}