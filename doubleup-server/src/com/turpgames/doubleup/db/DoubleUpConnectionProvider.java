package com.turpgames.doubleup.db;

import com.turpgames.db.IConnectionProvider;
import com.turpgames.doubleup.server.ServerConfig;

public class DoubleUpConnectionProvider implements IConnectionProvider {

	@Override
	public String getConnectionProvider() {
		return ServerConfig.getJdbcDriver();
	}

	@Override
	public String getConnectionString() {
		return ServerConfig.getDbConnectionString();
	}

	@Override
	public String getUsername() {
		return ServerConfig.getDbUser();
	}

	@Override
	public String getPassword() {
		return ServerConfig.getDbPassword();
	}
}