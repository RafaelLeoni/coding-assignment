package com.rafaelleoni;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rafaelleoni.database.Database;
import com.rafaelleoni.database.Event;
import com.rafaelleoni.file.FileHandler;
import com.rafaelleoni.manager.EventManager;

public class ApplicationTest {
	
	@Mock private Database db;
	
	@InjectMocks EventManager em;
	
	@Before
	public void beforeTest() {
		MockitoAnnotations.initMocks(this);
		
		doNothing().when(db).open();
		doNothing().when(db).commit();
	}
	
	@Test
	public void testFileHandler() throws IOException {
		String path = "src/test/resource/input.txt";
		FileHandler fh = new FileHandler(path);
		
		String result = fh.read();
		assertNotNull(result);
	}
	
	@Test
	public void testEventManager() {
		List<Event> events = new ArrayList<>();
		
		Event event1 = new Event();
		event1.setId("scsmbstgra");
		event1.setState("STARTED");
		event1.setTimestamp(1491377495212L);
		event1.setType("APPLICATION_LOG");
		event1.setHost("12345");
		
		events.add(event1);
		
		Event event2 = new Event();
		event2.setId("scsmbstgra");
		event2.setState("FINISHED");
		event2.setTimestamp(1491377495217L);
		event2.setType("APPLICATION_LOG");
		event2.setHost("12345");
		
		events.add(event2);
		
		when(db.save(any())).thenReturn(new Object());
		when(db.query(anyString(), any(), anyMap()))
		.thenReturn(events.stream().collect(Collectors.toList()));
		
		em.handle(events);
	}
	
}
