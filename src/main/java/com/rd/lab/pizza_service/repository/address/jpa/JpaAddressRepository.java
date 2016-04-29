package com.rd.lab.pizza_service.repository.address.jpa;

import javax.persistence.EntityManager;

import com.rd.lab.pizza_service.domain.address.Address;
import com.rd.lab.pizza_service.repository.JpaCRUDRepository;

public class JpaAddressRepository extends JpaCRUDRepository<Address> {

	private static final String TABLE_NAME = "tb_address";

	public JpaAddressRepository(EntityManager em) {
		super(em);
	}

	@Override
	public Class<Address> getEntityClass() {
		return Address.class;
	}

	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected void updateEntity(Address retr, Address upd) {
		if (upd.getAddrLine() != null) {
			retr.setAddrLine(upd.getAddrLine());
		}
		if (upd.getPostalCode() != null) {
			retr.setPostalCode(upd.getPostalCode());
		}
		if (upd.getCity() != null) {
			retr.setCity(upd.getCity());
		}
		if (upd.getCountry() != null) {
			retr.setCountry(upd.getCountry());
		}
		if (upd.getCust() != null) {
			retr.setCust(upd.getCust());
		}
	}

	@Override
	protected int getEntityId(Address entity) {
		return entity.getId();
	}

}
