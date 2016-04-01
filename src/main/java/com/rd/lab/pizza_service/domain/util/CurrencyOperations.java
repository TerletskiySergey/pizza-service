package com.rd.lab.pizza_service.domain.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface CurrencyOperations {
	RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;
	int MAX_FRACTION_DIGITS = 2;

	static BigDecimal setCurrencyScale(BigDecimal value) {
		return value.setScale(MAX_FRACTION_DIGITS, DEFAULT_ROUNDING_MODE);
	}

	static BigDecimal addCosts(BigDecimal val1, BigDecimal val2) {
		val1 = setCurrencyScale(val1);
		val2 = setCurrencyScale(val2);
		return val1.add(val2);
	}

	static BigDecimal subtractCosts(BigDecimal minuend, BigDecimal subtrahend) {
		minuend = setCurrencyScale(minuend);
		subtrahend = setCurrencyScale(subtrahend);
		return minuend.subtract(subtrahend);
	}

	static BigDecimal takePercent(BigDecimal value, double perc) {
		BigDecimal toTake = new BigDecimal(perc / 100);
		return setCurrencyScale(value.multiply(toTake));
	}
}