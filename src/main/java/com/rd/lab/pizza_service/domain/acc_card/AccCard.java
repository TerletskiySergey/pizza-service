package com.rd.lab.pizza_service.domain.acc_card;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

@Component
@Scope("prototype")
public class AccCard {
	private static Integer count = 0;

	private BigDecimal amount;
	private Integer id;

	public AccCard() {
		this(BigDecimal.ZERO);
	}

	@Autowired
	public AccCard(@Value("100") BigDecimal amount) {
		super();
		this.id = ++count;
		this.amount = CurrencyOperations.setCurrencyScale(amount);
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
		this.amount = CurrencyOperations.setCurrencyScale(amount);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AccCard [id=" + id + ", amount=" + amount + "]";
	}
}