package com.rd.lab.pizza_service.repository.order_status.jpa;

import javax.persistence.EntityManager;

import com.rd.lab.pizza_service.domain.order_status.Status;
import com.rd.lab.pizza_service.repository.JpaCRUDRepository;

public class JpaOrderStatusRepository extends JpaCRUDRepository<Status> {

	private static final String TABLE_NAME = "tb_order_status_history";

	public JpaOrderStatusRepository(EntityManager em) {
		super(em);
	}

	@Override
	protected Class<Status> getEntityClass() {
		return Status.class;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected void updateEntity(Status retr, Status upd) {

	}

	@Override
	protected int getEntityId(Status entity) {
		return entity.getId();
	}

}
