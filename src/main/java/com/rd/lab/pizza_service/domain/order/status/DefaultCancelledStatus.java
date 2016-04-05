package com.rd.lab.pizza_service.domain.order.status;

public class DefaultCancelledStatus implements Status {
	private static final String STRING_REPRESENTATION = "CANCELLED";

	@Override
	public int getPriority() {
		return MAX_STATUS_PRIORITY;
	}

	@Override
	public String toString() {
		return STRING_REPRESENTATION;
	}
}