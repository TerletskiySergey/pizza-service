package com.rd.lab.pizza_service.domain.acc_card;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.domain.util.CurrencyOperations;

@Component
@Scope("prototype")
@Entity
@Table(name = "tb_acc_card")
public class AccCard {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private BigDecimal amount;
	@OneToOne
	@JoinColumn(name = "cust_id", nullable = false)
	private Customer cust;

	public AccCard() {
	}

	public void addAmount(BigDecimal toAdd) {
		this.amount = CurrencyOperations.addCosts(amount, toAdd);
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Customer getCust() {
		return cust;
	}

	public Integer getId() {
		return id;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = CurrencyOperations.setCurrencyScale(amount);
	}

	public void setCust(Customer cust) {
		this.cust = cust;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "AccCard [id=" + id + ", amount=" + amount + "]";
	}
}