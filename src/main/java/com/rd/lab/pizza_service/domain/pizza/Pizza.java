package com.rd.lab.pizza_service.domain.pizza;

import java.math.BigDecimal;

import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

public class Pizza {
	public enum Type {
		Meat, Sea, Vegetarian
	}

	private static int count;

	private Integer id;
	private String name;
	private BigDecimal price;
	private Type type;

	public Pizza() {
		super();
	}

	public Pizza(String name, BigDecimal price, Type type) {
		super();
		this.id = ++count;
		this.name = name;
		this.price = CurrencyOperations.setCurrencyScale(price);
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Type getType() {
		return type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(BigDecimal price) {
		this.price = CurrencyOperations.setCurrencyScale(price);
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Pizza [id=" + id + ", name=" + name + ", price=" + price + ", type=" + type + "]";
	}
}