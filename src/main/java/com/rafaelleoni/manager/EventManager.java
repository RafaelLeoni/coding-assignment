package com.rafaelleoni.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.rafaelleoni.database.Database;
import com.rafaelleoni.database.Event;
import com.rafaelleoni.database.EventLog;

public class EventManager {
	
	private Database db;
	
	public EventManager() {
		db = new Database();
	}
	
	public void handle(List<Event> events) {
		db.open();
		events.forEach(db::save);
		
		Set<String> ids = events.stream().map(e -> e.getId()).collect(Collectors.toSet());
		
		ids.forEach(id -> {
			Map<String, Object> params = new HashMap<>();
			params.put("id", id);
			List<Event> evs = db.query("from Event where id = :id", Event.class, params);
			
			EventLog log = new EventLog();
			log.setId(evs.get(0).getId());
			log.setDuration((int) Math.abs(evs.get(1).getTimestamp() - evs.get(0).getTimestamp()));
			log.setType(evs.get(0).getType());
			log.setHost(evs.get(0).getHost());
			log.setAlert(log.getDuration() > 4);
			
			db.save(log);
		});
		
		db.commit();
	}

}
