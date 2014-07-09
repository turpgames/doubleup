package com.turpgames.doubleup.migration;

import com.turpgames.server.db.IConnectionProvider;

class MySqlConnectionProviderImpl implements IConnectionProvider {
	private final String connStr;

	public MySqlConnectionProviderImpl(String connStr) {
		this.connStr = connStr;
	}

	@Override
	public String getConnectionProvider() {
		return "com.mysql.jdbc.Driver";
	}

	@Override
	public String getConnectionString() {
		return connStr;
	}

	@Override
	public String getUsername() {
		return "turpgames";
	}

	@Override
	public String getPassword() {
		return "Tu1234Ga";
	}
}