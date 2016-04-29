package com.rd.lab.pizza_service.domain.order_status;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "IN_PROGRESS")
public class DefaultInProgressStatus extends Status {
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