package com.rd.lab.pizza_service.repository.customer.jpa;

import javax.persistence.EntityManager;

import com.rd.lab.pizza_service.domain.customer.Customer;
import com.rd.lab.pizza_service.repository.JpaCRUDRepository;

public class JpaCustomerRepository extends JpaCRUDRepository<Customer> {

	private static final String TABLE_NAME = "tb_customer";

	public JpaCustomerRepository(EntityManager em) {
		super(em);
	}

	@Override
	protected Class<Customer> getEntityClass() {
		return Customer.class;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected void updateEntity(Customer retr, Customer upd) {
		if (upd.getAddr() != null) {
			retr.setAddr(upd.getAddr());
		}
		if (upd.getCard() != null) {
			retr.setCard(upd.getCard());
		}
		if (upd.getName() != null) {
			retr.setName(upd.getName());
		}
		if (upd.getOrders() != null) {
			retr.setOrders(upd.getOrders());
		}
	}

	@Override
	protected int getEntityId(Customer entity) {
		return entity.getId();
	}
}
