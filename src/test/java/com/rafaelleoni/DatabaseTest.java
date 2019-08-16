package com.rafaelleoni;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rafaelleoni.database.Database;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DatabaseTest {
	
	@Mock private EntityManager manager;
	@Mock TypedQuery query;
	
	@InjectMocks Database db;
	
	@Before
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testDatabaseFindAll() {
		when(manager.createQuery(anyString(), any())).thenReturn(query);
		when(query.getResultList()).thenReturn(new ArrayList<>());
		
		assertNotNull(db.findAll(Object.class));
	}
	
	@Test
	public void testDatabaseFindById() {
		when(manager.find(any(), any(Object.class))).thenReturn(new Object());
		
		assertNotNull(db.findById("key", Object.class));
	}
	
	@Test
	public void testDatabasesSave() {
		doNothing().when(manager).persist(any());
		
		assertNotNull(db.save(Object.class));
	}
	
	@Test
	public void testDatabasesUpdate() {
		when(manager.merge(any())).thenReturn(new Object());
		
		assertNotNull(db.update(Object.class));
	}
	
	@Test
	public void testDatabasesDelete() {
		when(manager.find(any(), any(Object.class))).thenReturn(new Object());
		when(manager.merge(any())).thenReturn(new Object());
		
		db.delete("key", Object.class);
	}


}
