package com.rafaelleoni.database;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class Database {
	
	private EntityManagerFactory factory;
	private EntityManager manager;
	
	public Database() {
		factory = Persistence.createEntityManagerFactory("coding-assignment");
		manager = factory.createEntityManager();
	}
	
	public void open() {
		manager.getTransaction().begin();
	}
	
	public void commit() {
		manager.flush();
		manager.getTransaction().commit();
		manager.clear();
		manager.close();
		factory.close();
	}
	
	public <T> List<T> findAll(Class<T> type) {
		TypedQuery<T> query = manager
				.createQuery(String.format("from %s", type.getSimpleName()), type);
		return query.getResultList();
	}
	
	public <T,I> T findById(I id, Class<T> type) {
		return manager.find(type, id);
	}
	
	public <T> T save(T entity) {
		manager.persist(entity);
		return entity;
	}
	
	public <T> T update(T entity) {
		manager.merge(entity);
		return entity;
	}
	
	public <T, I> void delete(I id, Class<T> type) {
		T entity = findById(id, type);
		T mergedEntity = (T) manager.merge(entity);
		manager.remove(mergedEntity);
	}
	
	public <T> List<T> query(String query, Class<T> type, Map<String, Object> params) {
		TypedQuery<T> typedQuery = manager.createQuery(query, type);
		params.forEach((key, value) -> {
			typedQuery.setParameter(key, value);
		});
		return typedQuery.getResultList();
	}
	
}
