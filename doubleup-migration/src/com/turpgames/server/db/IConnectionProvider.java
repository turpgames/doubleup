package com.turpgames.server.db;

public interface IConnectionProvider {
	String getConnectionProvider();

	String getConnectionString();

	String getUsername();

	String getPassword();
}
