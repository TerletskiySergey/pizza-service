package com.rd.lab.pizza_service.repository.pizza.jpa;

import javax.persistence.EntityManager;

import com.rd.lab.pizza_service.domain.pizza.Pizza;
import com.rd.lab.pizza_service.repository.JpaCRUDRepository;

public class JpaPizzaRepository extends JpaCRUDRepository<Pizza> {

	private static final String TABLE_NAME = "tb_pizza";

	public JpaPizzaRepository(EntityManager em) {
		super(em);
	}

	@Override
	protected Class<Pizza> getEntityClass() {
		return Pizza.class;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected void updateEntity(Pizza retr, Pizza upd) {
		if (upd.getName() != null) {
			retr.setName(upd.getName());
		}
		if (upd.getOrders() != null) {
			retr.setOrders(upd.getOrders());
		}
		if (upd.getPrice() != null) {
			retr.setPrice(upd.getPrice());
		}
		if (upd.getType() != null) {
			retr.setType(upd.getType());
		}
	}

	@Override
	protected int getEntityId(Pizza entity) {
		return entity.getId();
	}

}
