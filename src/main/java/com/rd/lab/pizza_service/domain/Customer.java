package com.rd.lab.pizza_service.domain;

public class Customer {
	private static int count;
	private Integer id;
	private String name;
	private Address addr;
	private AccCard card;

	public Customer(String name, Address addr) {
		this(name, addr, null);
	}

	public Customer(String name, Address addr, AccCard card) {
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
