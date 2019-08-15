package com.rafaelleoni.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileRead implements Runnable {
	
	private final Logger LOGGER = LoggerFactory.getLogger(FileRead.class);
	
	private FileChannel channel;
	private long start;
	private int size;
	private int sequence;
	
	private Map<Integer, String> result;
	
	public FileRead(FileChannel channel, long start, int size, int sequence, Map<Integer, String> result) {
		this.channel = channel;
		this.start = start;
		this.size = size;
		this.sequence = sequence;
		this.result = result;
	}

	@Override
	public void run() {
		LOGGER.debug("Reading the channel: [" + sequence + "](" + start + ":" + size + ")");
		
		try {
			ByteBuffer buff = ByteBuffer.allocate(size);
			channel.read(buff, start);
			
			String chunk = new String(buff.array(), Charset.forName("UTF-8"));
			result.put(sequence, chunk);			
			
			LOGGER.debug("Done Reading the channel: [" + sequence + "](" + start + ":" + size + ")");
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		
	}

}
