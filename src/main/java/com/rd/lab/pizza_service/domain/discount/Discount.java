package com.rd.lab.pizza_service.domain.discount;

import java.math.BigDecimal;

import com.rd.lab.pizza_service.domain.Order;

public abstract class Discount {
	private static Integer count = 0;

	private Integer id;
	private String description;
	private String title;

	public Discount(String title) {
		super();
		this.id = ++count;
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public abstract BigDecimal getDiscount(Order order);

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}