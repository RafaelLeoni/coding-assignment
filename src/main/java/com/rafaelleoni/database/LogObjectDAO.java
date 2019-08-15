package com.rafaelleoni.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rafaelleoni.LogObject;

public class LogObjectDAO extends Database<LogObject> {
	
	private final Logger LOGGER = LoggerFactory.getLogger(LogObjectDAO.class);

	protected LogObject objectMapper(ResultSet rs) throws SQLException {
		LogObject log = new LogObject();
		log.setId(rs.getString("id"));
		log.setState(rs.getString("state"));
		log.setTimestamp(rs.getLong("timestamp"));
		log.setType(rs.getString("type"));
		log.setHost(rs.getString("host"));
		return log;
	}
	
	@Override
	public List<LogObject> findAll() {
		List<LogObject> logs = new ArrayList<>();
		try {
			ResultSet rs = executeQuery("SELECT * FROM log");
			while (rs.next()) {
				logs.add(objectMapper(rs));
			}
			shutdown();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return logs;
	}

	@Override
	public LogObject findById(String id) {
		LogObject log = null;
		try {
			ResultSet rs = executeQuery("SELECT * FROM log WHERE id = " + id);
			log = objectMapper(rs);
			shutdown();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return log;
	}
	
}
