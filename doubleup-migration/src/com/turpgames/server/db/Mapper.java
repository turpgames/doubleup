package com.turpgames.server.db;

import java.sql.ResultSet;

public class Mapper<T> {
	private TableMapping<T> mapping;
	
	public T create(ResultSet rs) {
		try {
			T entity = mapping.getEntityClass().newInstance();
			
			return entity;
		}
		catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
}
