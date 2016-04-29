package com.rd.lab.pizza_service.domain.order_status;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CANCELLED")
public class DefaultCancelledStatus extends Status {
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