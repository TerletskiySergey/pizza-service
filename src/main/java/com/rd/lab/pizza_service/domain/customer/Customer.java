package com.rd.lab.pizza_service.domain.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.domain.address.Address;

@Component
public class Customer {
	private static int count;
	private Integer id;
	private String name;
	private Address addr;
	private AccCard card;

	public Customer(String name, Address addr) {
		this(name, addr, null);
	}

	@Autowired
	public Customer(@Value("defaultCustomer") String name, Address addr, AccCard card) {
		super();
		this.id = ++count;
		this.name = name;
		this.addr = addr;
		this.card = card;
	}

	public Address getAddr() {
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

	public void setAddr(Address addr) {
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

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", addr=" + addr + ", card=" + card + "]";
	}
}
