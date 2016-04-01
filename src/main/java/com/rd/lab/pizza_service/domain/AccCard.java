package com.rd.lab.pizza_service.domain;

import java.math.BigDecimal;

import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

public class AccCard {
	private static Integer count = 0;

	private Integer id;
	private BigDecimal amount;

	public AccCard() {
		this(new BigDecimal("0"));
	}

	public AccCard(BigDecimal amount) {
		super();
		this.id = ++count;
		this.amount = CurrencyOperations.setDefaultScale(amount);
	}

	public void addAmount(BigDecimal toAdd) {
		this.amount = CurrencyOperations.addCosts(amount, toAdd);
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Integer getId() {
		return id;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = CurrencyOperations.setDefaultScale(amount);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AccCard [id=" + id + ", amount=" + amount + "]";
	}
}