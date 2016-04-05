package com.rd.lab.pizza_service.domain.discount;

import java.math.BigDecimal;

public interface Discount {

	BigDecimal getDiscount();

	void updateInstance(Object input);

	boolean isApplicableFor(Object obj);
}