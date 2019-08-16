package com.rafaelleoni.manager;

import java.util.List;

import com.rafaelleoni.Event;
import com.rafaelleoni.database.Database;

public class EventManager {
	
	private Database db;
	
	public EventManager() {
		db = new Database();
	}
	
	public void handle(List<Event> logs) {
		logs.forEach(log -> {
			db.save(log);
			
			
			
		});
	}

}
