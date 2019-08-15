package com.rafaelleoni.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileHandler {
	
	private final Logger LOGGER = LoggerFactory.getLogger(FileHandler.class);
	private final int CHUNK_SIZE = 1024 * 100; //Define file size to 100MB
	
	private String path;
	private Map<Integer, String> result; 
	
	public FileHandler(String path) {
		this.path = path;
		this.result = new HashMap<>();
	}
	
	public String read() throws IOException {
		FileInputStream fis = new FileInputStream(path);
		FileChannel channel = fis.getChannel();
		long remainingSize = channel.size();
		int threads = (int) Math.ceil((double) remainingSize / CHUNK_SIZE);
		
		ExecutorService executor = Executors.newFixedThreadPool(threads);

		LOGGER.info("Starting threads");
		
		long startLocation = 0; //file pointer
	    int i = 0; //loop counter
	    while (remainingSize >= CHUNK_SIZE) {
	        executor.execute(new FileRead(channel, startLocation, CHUNK_SIZE, i++, result));
	        remainingSize = remainingSize - CHUNK_SIZE;
	        startLocation = startLocation + CHUNK_SIZE;
	    }
	    //load the last remaining piece
	    executor.execute(new FileRead(channel, startLocation, CHUNK_SIZE, i++, result));
	    
	    executor.shutdown();
	    
	    //Wait for all threads to finish
	    while (!executor.isTerminated());
	    
	    LOGGER.info("Finished all threads");
	    fis.close();
	    
	    return result.entrySet().stream().sorted(Map.Entry.comparingByKey())
    		.map(entry -> entry.getValue()).collect(Collectors.joining());
	}

}
