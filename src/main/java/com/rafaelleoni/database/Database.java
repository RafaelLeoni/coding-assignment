package com.rafaelleoni.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Database<T> {
	
	private final Logger LOGGER = LoggerFactory.getLogger(Database.class);
	
	private Statement stm;
	
	protected Database() {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Connection con = DriverManager
				.getConnection("jdbc:hsqldb:file:C:/dev/dit4mz38/workspace/coding-assignment/src/main/resource/db", "sa", "");
			stm = con.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}
	
	protected ResultSet executeQuery(String query) throws SQLException {
		return stm.executeQuery(query);
	}
	
	protected void shutdown() throws SQLException {
		stm.execute("SHUTDOWN");
	}
	
	protected abstract T objectMapper(ResultSet rs) throws SQLException;
	
	protected abstract List<T> findAll();
	
	protected abstract T findById(String id);

}
