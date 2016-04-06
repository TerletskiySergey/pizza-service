package com.rd.lab.pizza_service.domain.order.status;

public class DefaultDoneStatus implements Status {
	private static final String STRING_REPRESENTATION = "DONE";

	public DefaultDoneStatus() {
		super();
	}

	@Override
	public int getPriority() {
		return MAX_STATUS_PRIORITY;
	}

	@Override
	public String toString() {
		return STRING_REPRESENTATION;
	}

}