package com.rd.lab.pizza_service.repository.acc_card.jpa;

import javax.persistence.EntityManager;

import com.rd.lab.pizza_service.domain.acc_card.AccCard;
import com.rd.lab.pizza_service.repository.JpaCRUDRepository;

public class JpaAccCardRepository extends JpaCRUDRepository<AccCard> {

	private static final String TABLE_NAME = "tb_acc_card";

	public JpaAccCardRepository(EntityManager em) {
		super(em);
	}

	@Override
	protected Class<AccCard> getEntityClass() {
		return AccCard.class;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected void updateEntity(AccCard retr, AccCard upd) {
		if (upd.getCust() != null) {
			retr.setCust(upd.getCust());
		}
		if (upd.getAmount() != null) {
			retr.setAmount(upd.getAmount());
		}
	}

	@Override
	protected int getEntityId(AccCard entity) {
		return entity.getId();
	}

}
