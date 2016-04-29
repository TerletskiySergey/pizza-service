package com.rd.lab.pizza_service.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public abstract class JpaCRUDRepository<T> {

	protected static final String SELECT_QUERY = "SELECT entity FROM pizza_service.%s entity";

	protected EntityManager em;

	protected abstract Class<T> getEntityClass();

	protected abstract String getTableName();

	protected abstract void updateEntity(T retr, T upd);

	protected abstract int getEntityId(T entity);

	protected JpaCRUDRepository(EntityManager em) {
		this.em = em;
	}

	public T save(T obj) {
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		return obj;
	}

	public T find(int id) {
		return em.find(getEntityClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		String queryString = String.format(SELECT_QUERY, getTableName());
		Query query = em.createQuery(queryString, getEntityClass());
		return query.getResultList();
	}

	public boolean delete(int id) {
		T entity = em.find(getEntityClass(), id);
		if (entity != null) {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
			return true;
		}
		return false;
	}

	public T update(T updated) {
		T retrieved = em.find(getEntityClass(), getEntityId(updated));
		if (retrieved != null) {
			em.getTransaction().begin();
			updateEntity(retrieved, updated);
			em.getTransaction().commit();
		}
		return retrieved;
	}
}