package com.rafaelleoni.database;

import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Database {
	
	private SessionFactory factory;
	private Session session;
	
	public Database() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
		         applySettings(configuration.getProperties());
		factory = configuration.buildSessionFactory(builder.build());
		session = factory.openSession();
	}
	
	public void close() {
		session.close();
		factory.close();
	}
	
	public <T> List<T> findAll(Class<T> type) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(type);
		return session.createQuery(query).getResultList();
	}
	
	public <T,I> T findById(I id, Class<T> type) {
		return session.find(type, id);
	}
	
	public <T> T save(T entity) {
		EntityTransaction tx = session.getTransaction();
		tx.begin();
		session.persist(entity);
		session.flush();
		tx.commit();
		return entity;
	}
	
	public <T> T update(T entity) {
		EntityTransaction tx = session.getTransaction();
		tx.begin();
		session.merge(entity);
		session.flush();
		tx.commit();
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public <T, I> void delete(I id, Class<T> type) {
		T entity = findById(id, type);
		EntityTransaction tx = session.getTransaction();
		tx.begin();
		T mergedEntity = (T) session.merge(entity);
		session.remove(mergedEntity);
		session.flush();
		tx.commit();
	}
	
}
