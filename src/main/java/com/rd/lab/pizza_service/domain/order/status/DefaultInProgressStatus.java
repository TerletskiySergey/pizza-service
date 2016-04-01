package com.rd.lab.pizza_service.domain.order.status;

public class DefaultInProgressStatus implements Status {
	private static final int STATUS_PRIORITY = 1;

	@Override
	public int getPriority() {
		return STATUS_PRIORITY;
	}

	@Override
	public String toString() {
		return "DefaultInProgressStatus";
	}
}