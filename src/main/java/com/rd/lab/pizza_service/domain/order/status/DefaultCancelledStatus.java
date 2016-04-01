package com.rd.lab.pizza_service.domain.order.status;

public class DefaultCancelledStatus implements Status {

	@Override
	public int getPriority() {
		return MAX_STATUS_PRIORITY;
	}

	@Override
	public String toString() {
		return "DefaultCancelledStatus";
	}
}