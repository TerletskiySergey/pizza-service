package com.rd.lab.pizza_service.repository.order.jpa;

import javax.persistence.EntityManager;

import com.rd.lab.pizza_service.domain.order.Order;
import com.rd.lab.pizza_service.repository.JpaCRUDRepository;

public class JpaOrderRepository extends JpaCRUDRepository<Order> {

	private static final String TABLE_NAME = "tb_order";

	public JpaOrderRepository(EntityManager em) {
		super(em);
	}

	@Override
	protected Class<Order> getEntityClass() {
		return Order.class;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected void updateEntity(Order retrv, Order upd) {
		if (upd.getPizzas() != null) {
			retrv.setPizzas(upd.getPizzas());

		}
		if (upd.getCustomer() != null) {
			retrv.setCustomer(upd.getCustomer());
		}
		if (upd.getCost() != null) {
			retrv.setCost(upd.getCost());
		}
		if (upd.getStatusHistory() != null) {
			retrv.setStatusHistory(upd.getStatusHistory());
		}
	}

	@Override
	protected int getEntityId(Order entity) {
		return entity.getId().intValue();
	}
}
