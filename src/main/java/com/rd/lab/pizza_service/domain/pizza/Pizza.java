package com.rd.lab.pizza_service.domain.pizza;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.domain.util.CurrencyOperations;
import com.rd.lab.pizza_service.repository.pizza.jpa.PizzaTypeConverter;

@Entity
@Table(name = "tb_pizza")
public class Pizza {
	@Entity
	@Table(name = "tb_pizza_type")
	public enum Type {

		Meat, Sea, Vegetarian;

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		@Column(nullable = false, unique = true)
		private String name = toString().toUpperCase();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private BigDecimal price;
	@Convert(converter = PizzaTypeConverter.class)
	private Type type;
	@ManyToMany(mappedBy = "pizzas", fetch = FetchType.LAZY)
	private List<Order> orders;

	public Pizza() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Order> getOrders() {
		return orders;
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

	public void setOrders(List<Order> orders) {
		this.orders = orders;
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