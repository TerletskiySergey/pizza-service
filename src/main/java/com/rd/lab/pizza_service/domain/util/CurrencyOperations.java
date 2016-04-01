package com.rd.lab.pizza_service.domain.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface CurrencyOperations {
	RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;
	int MAX_FRACTION_DIGITS = 2;

	static BigDecimal setDefaultScale(BigDecimal value) {
		return value.setScale(MAX_FRACTION_DIGITS, DEFAULT_ROUNDING_MODE);
	}

	static BigDecimal addCosts(BigDecimal val1, BigDecimal val2) {
		val1 = setDefaultScale(val1);
		val2 = setDefaultScale(val2);
		return val1.add(val2);
	}

	static BigDecimal subtractCosts(BigDecimal minuend, BigDecimal subtrahend) {
		minuend = setDefaultScale(minuend);
		subtrahend = setDefaultScale(subtrahend);
		return minuend.subtract(subtrahend);
	}

	static BigDecimal takePercent(BigDecimal value, String perc) {
		BigDecimal toTake = new BigDecimal(perc);
		return setDefaultScale(value.multiply(toTake));
	}
}