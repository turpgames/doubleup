package com.turpgames.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IEntityFactory<T> {
	T create(ResultSet rs) throws SQLException;
}