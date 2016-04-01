package com.rd.lab.pizza_service.domain.discount;

import java.math.BigDecimal;

public interface Discount<T> {

	BigDecimal getDiscount();

	void updateInstance(T input);
}