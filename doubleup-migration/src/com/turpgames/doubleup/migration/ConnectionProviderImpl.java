package com.turpgames.doubleup.migration;

import com.turpgames.server.db.IConnectionProvider;

class ConnectionProviderImpl implements IConnectionProvider {
	private final String connStr;

	public ConnectionProviderImpl(String connStr) {
		this.connStr = connStr;
	}

	@Override
	public String getConnectionProvider() {
		return "org.h2.Driver";
	}

	@Override
	public String getConnectionString() {
		return connStr;
	}

	@Override
	public String getUsername() {
		return "sa";
	}

	@Override
	public String getPassword() {
		return "Tu1234Ga";
	}
}