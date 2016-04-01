package com.rd.lab.pizza_service.domain.discount;

import java.math.BigDecimal;

import com.rd.lab.pizza_service.domain.AccCard;
import com.rd.lab.pizza_service.domain.Order;
import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

public class AccCardDiscount_10_30Max extends Discount {
	private static final String title = "ACCUMULATION_CARD_DISCOUNT_10_30MAX";
	private static final int CARD_DISCOUNT_PERCENTAGE = 10;
	private static final int MAX_DISCOUNT_PERCENTAGE = 30;

	private AccCard card;

	public AccCardDiscount_10_30Max() {
		this(null);
	}

	public AccCardDiscount_10_30Max(AccCard card) {
		super(title);
		this.card = card;
	}

	public AccCard getCard() {
		return card;
	}

	public void setCard(AccCard card) {
		this.card = card;
	}

	@Override
	public BigDecimal getDiscount(Order order) {
		BigDecimal disc = CurrencyOperations.takePercent(card.getAmount(),
				String.valueOf(CARD_DISCOUNT_PERCENTAGE));
		BigDecimal maxDisc = CurrencyOperations.takePercent(order.getFullCost(),
				String.valueOf(MAX_DISCOUNT_PERCENTAGE));
		return (disc.compareTo(maxDisc) >= 0) ? maxDisc : disc;
	}
}
