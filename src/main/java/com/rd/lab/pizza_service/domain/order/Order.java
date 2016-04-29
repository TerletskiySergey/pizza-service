package com.rd.lab.pizza_service.domain.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.order_status.Status;
import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

@Entity
@Table(name = "tb_order")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "tb_order_status_history", joinColumns = @JoinColumn(name = "order_id"))
	@MapKeyJoinColumn(name = "status_id")
	@Column(name = "date", nullable = true)
	private Map<Status, Date> statusHistory;

	private BigDecimal cost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cust_id", nullable = true)
	private Customer customer;

	@ManyToMany
	@JoinTable(name = "tb_order_pizza", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "pizza_id"))
	private List<Pizza> pizzas;

	public BigDecimal getCost() {
		if (this.cost == null) {
			this.cost = BigDecimal.ZERO;
		}
		if (pizzas != null) {
			BigDecimal cost = BigDecimal.ZERO;
			for (Pizza p : pizzas) {
				cost = CurrencyOperations.addCosts(cost, p.getPrice());
			}
			this.cost = cost;
		}
		return cost;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Integer getId() {
		return id;
	}

	public List<Pizza> getPizzas() {
		return pizzas;
	}

	public Status getStatus() {
		return getCurrentStatus();
	}

	public Map<Status, Date> getStatusHistory() {
		return statusHistory;
	}

	public void setCost(BigDecimal cost) {
		this.cost = CurrencyOperations.setCurrencyScale(cost);
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPizzas(List<Pizza> pizzas) {
		this.pizzas = pizzas;
		getCost();
	}

	public boolean setStatus(Status st) {
		Status curStatus = getCurrentStatus();
		if (curStatus == null || curStatus.canBeChangedTo(st)) {
			statusHistory.put(st, new Date());
			st.init();
			return true;
		}
		return false;
	}

	public void setStatusHistory(Map<Status, Date> statusHistory) {
		this.statusHistory = statusHistory;
	}

	private Status getCurrentStatus() {
		Map.Entry<Status, Date> result = null;
		for (Map.Entry<Status, Date> curEntity : statusHistory.entrySet()) {
			if (result == null) {
				result = curEntity;
			} else if (curEntity.getValue().compareTo(result.getValue()) > 0) {
				result = curEntity;
			}
		}
		return (result != null) ? result.getKey() : null;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", status=" + getCurrentStatus() + ", cost=" + cost
				+ ", customer=" + customer + ", pizzas=" + pizzas + "]";
	}

}