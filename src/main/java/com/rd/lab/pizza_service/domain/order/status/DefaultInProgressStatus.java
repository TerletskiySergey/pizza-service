package com.rd.lab.pizza_service.domain.order.status;

public class DefaultInProgressStatus implements Status {
	private static final int STATUS_PRIORITY = 1;
	private static final String STRING_REPRESENTATION = "IN_PROGRESS";

	@Override
	public int getPriority() {
		return STATUS_PRIORITY;
	}

	@Override
	public String toString() {
		return STRING_REPRESENTATION;
	}
}