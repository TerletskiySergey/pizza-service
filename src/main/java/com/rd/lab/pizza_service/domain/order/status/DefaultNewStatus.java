package com.rd.lab.pizza_service.domain.order.status;

import java.util.List;

import com.rd.lab.pizza_service.domain.pizza.Pizza;

public class DefaultNewStatus implements Status {
	private static final int STATUS_PRIORITY = 0;

	@Override
	public int add(List<Pizza> toAdd, int limit) {
		return (limit > 0 && toAdd.size() <= limit) ? limit - toAdd.size() : 0;
	}

	@Override
	public int getPriority() {
		return STATUS_PRIORITY;
	}

	@Override
	public String toString() {
		return "DefaultNewStatus";
	}

}