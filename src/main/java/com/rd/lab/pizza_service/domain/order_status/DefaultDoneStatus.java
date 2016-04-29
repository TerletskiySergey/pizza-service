package com.rd.lab.pizza_service.domain.order_status;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "DONE")
public class DefaultDoneStatus extends Status {
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