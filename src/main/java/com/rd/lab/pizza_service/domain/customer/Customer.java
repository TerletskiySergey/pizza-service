package com.rd.lab.pizza_service.domain.customer;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.domain.address.Address;
import com.rd.lab.pizza_service.domain.order.Order;

@Component
@Entity
@Table(name = "tb_customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Integer id;
	@Column(nullable = false)
	private String name;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tb_cust_addr", joinColumns = @JoinColumn(name = "cust_id"), inverseJoinColumns = @JoinColumn(name = "addr_id"))
	private List<Address> addr;
	@OneToOne(mappedBy = "cust")
	private AccCard card;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private List<Order> orders;

	public List<Address> getAddr() {
		return addr;
	}

	public AccCard getCard() {
		return card;
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

	public void setAddr(List<Address> addr) {
		this.addr = addr;
	}

	public void setCard(AccCard card) {
		this.card = card;
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

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", addr=" + addr + ", card=" + card + "]";
	}
}
