package com.rd.lab.pizza_service.domain.order.status;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.domain.order.Order;

public class DefaultDoneStatus implements Status {

	private Order order;

	public DefaultDoneStatus(Order order) {
		super();
		this.order = order;
	}

	@Override
	public void init() {
		AccCard card = order.getCustomer().getCard();
		card.addAmount(order.getCost());
	}

	@Override
	public int getPriority() {
		return MAX_STATUS_PRIORITY;
	}

	@Override
	public String toString() {
		return "DefaultDoneStatus";
	}

}