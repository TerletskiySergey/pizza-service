package com.rd.lab.pizza_service.domain.order.status;

import java.util.List;

import com.rd.lab.pizza_service.domain.pizza.Pizza;

public interface Status {
	public static final int MAX_STATUS_PRIORITY = Integer.MAX_VALUE;

	default void init() {
	}

	default int add(List<Pizza> toAdd, int limit) {
		return 0;
	}

	int getPriority();

	default boolean setStatus(Status status) {
		return this.getPriority() <= status.getPriority();
	}
}