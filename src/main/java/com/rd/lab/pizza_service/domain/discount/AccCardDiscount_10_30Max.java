package com.rd.lab.pizza_service.domain.discount;

import java.math.BigDecimal;
import java.util.List;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

public class AccCardDiscount_10_30Max implements Discount {

	private static final int CARD_DISCOUNT_PERCENTAGE = 10;
	private static final int MAX_DISCOUNT_PERCENTAGE = 30;

	private AccCard card;
	private List<Pizza> pizzas;

	public AccCardDiscount_10_30Max() {
		super();
	}

	public AccCardDiscount_10_30Max(List<Pizza> pizzas, AccCard card) {
		super();
		this.pizzas = pizzas;
		this.card = card;
	}

	@Override
	public BigDecimal getDiscount() {
		if (card == null || pizzas == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal disc = CurrencyOperations.takePercent(card.getAmount(),
				CARD_DISCOUNT_PERCENTAGE);
		BigDecimal maxDisc = CurrencyOperations.takePercent(getPizzasPrice(),
				MAX_DISCOUNT_PERCENTAGE);
		return (disc.compareTo(maxDisc) >= 0) ? maxDisc : disc;
	}

	private BigDecimal getPizzasPrice() {
		BigDecimal cost = BigDecimal.ZERO;
		for (Pizza p : pizzas) {
			cost = CurrencyOperations.addCosts(cost, p.getPrice());
		}
		return cost;
	}

	@Override
	public void updateInstance(Object input) {
		if (isApplicableFor(input)) {
			Order order = (Order) input;
			card = order.getCustomer().getCard();
			pizzas = order.getPizzas();
		}
	}

	@Override
	public boolean isApplicableFor(Object obj) {
		return obj.getClass() == Order.class;
	}
}
