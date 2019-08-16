package com.rafaelleoni;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelleoni.file.FileHandler;
import com.rafaelleoni.manager.EventManager;

public class Application {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
	public static void main(String ...args) {
		
		LOGGER.info("Starting application with args: " + String.join(", ", args));
		
		if (args.length != 1) 
			throw new IllegalArgumentException("You must pass the path file as argument.");
		
		String path = args[0];
		FileHandler fileHandler = new FileHandler(path);
		
		try {
			LOGGER.info("Reading file " + path);
			String result = fileHandler.read();
			
			//Converting file string to list of Log object
			List<Event> logs = new ArrayList<>();
			for (String line : result.split(System.lineSeparator())) {
				logs.add(new ObjectMapper().readValue(line, Event.class));
			}
			
			LOGGER.info("Saving events into database. Number of records: " + logs.size());
			EventManager manager = new EventManager();
			manager.handle(logs);
			
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
	}

}
