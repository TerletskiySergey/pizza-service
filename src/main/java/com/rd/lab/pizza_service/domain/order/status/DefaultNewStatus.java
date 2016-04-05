package com.rd.lab.pizza_service.domain.order.status;

import java.util.List;

import com.rd.lab.pizza_service.domain.pizza.Pizza;

public class DefaultNewStatus implements Status {
	private static final int STATUS_PRIORITY = 0;
	private static final String STRING_REPRESENTATION = "NEW";
	private Integer pizNum;

	public DefaultNewStatus() {
		this(0);
	}

	public DefaultNewStatus(int pizNum) {
		this.pizNum = pizNum;
	}

	@Override
	public int add(List<Pizza> toAdd, int limit) {
		int allowed = limit - pizNum;
		return (allowed > 0) ? pizNum += Math.min(allowed, toAdd.size()) : 0;
	}

	@Override
	public int getPriority() {
		return STATUS_PRIORITY;
	}

	@Override
	public String toString() {
		return STRING_REPRESENTATION;
	}
}