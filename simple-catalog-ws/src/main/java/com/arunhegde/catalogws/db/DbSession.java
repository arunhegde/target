package com.arunhegde.catalogws.db;

import org.dizitart.no2.Nitrite;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DbSession {
	private Nitrite db = null;
	private static DbSession dbSession = null;
	private final String dbFile;
	
	private DbSession(String dbFile) {
		this.dbFile = dbFile;
		init();
	}
	
	public static DbSession getInstance(String dbFile) {
		if(dbSession == null) {
			dbSession = new DbSession(dbFile);
		}
		
		return dbSession;
	}
	
	private void init() {
		ObjectMapper om;
		db = Nitrite.builder()
		        .compressed()
		        .filePath(dbFile)
		        .openOrCreate("user", "password");
	}
	
	public Nitrite getDb() {
		return db;
	}
}
