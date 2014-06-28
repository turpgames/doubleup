package com.turpgames.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

	private static IConnectionProvider connectionProvider;

	public static IConnectionProvider getConnectionProvider() {
		return connectionProvider;
	}

	public static void setConnectionProvider(IConnectionProvider connectionProvider) {
		DbManager.connectionProvider = connectionProvider;
	}

	private static Connection createConnection(IConnectionProvider connProv) throws ClassNotFoundException, SQLException {
		Class.forName(connProv.getConnectionProvider());
		return DriverManager.getConnection(connProv.getConnectionString(), connProv.getUsername(), connProv.getPassword());
	}

	private Connection connection;

	public DbManager() throws SQLException {
		this(false);
	}

	public DbManager(boolean useTransaction) throws SQLException {
		this(useTransaction, connectionProvider);
	}

	public DbManager(IConnectionProvider connProv) throws SQLException {
		this(false, connProv);
	}

	public DbManager(boolean useTransaction, IConnectionProvider connProv) throws SQLException {
		try {
			connection = createConnection(connProv);
			connection.setAutoCommit(!useTransaction);
		} catch (ClassNotFoundException ex) {
			throw new SQLException(ex.getMessage(), ex);
		}
	}

	private void commit() throws SQLException {
		if (connection != null && !connection.isClosed() && !connection.getAutoCommit()) {
			connection.commit();
		}
	}

	private void rollback() throws SQLException {
		if (connection != null && !connection.isClosed() && !connection.getAutoCommit()) {
			connection.rollback();
		}
	}

	private void close(boolean rollback) throws SQLException {
		if (rollback) {
			rollback();
		}
		else {
			commit();
		}
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}

	private PreparedStatement prepareStatement(SqlQuery sql, boolean returnGeneratedKeys) throws SQLException {
		PreparedStatement statement;
		if (returnGeneratedKeys)
			statement = connection.prepareStatement(sql.getQuery(), Statement.RETURN_GENERATED_KEYS);
		else
			statement = connection.prepareStatement(sql.getQuery());

		SqlParameter[] params = sql.getParameters();
		if (params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i].getValue(), params[i].getSqlType());
			}
		}
		return statement;
	}

	public void close() {
		try {
			close(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close();
	}

	public void abort() {
		try {
			close(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object insert(SqlQuery sql) throws SQLException {
		PreparedStatement statement = prepareStatement(sql, true);
		statement.executeUpdate();
		ResultSet resultSet = statement.getGeneratedKeys();
		return resultSet.next()
				? resultSet.getObject(1)
				: null;
	}

	public int update(SqlQuery sql) throws SQLException {
		PreparedStatement statement = prepareStatement(sql, false);
		return statement.executeUpdate();
	}

	public int delete(SqlQuery sql) throws SQLException {
		return update(sql);
	}

	public ResultSet select(SqlQuery sql) throws SQLException {
		PreparedStatement statement = prepareStatement(sql, false);
		return statement.executeQuery();
	}

	public static Object executeInsert(SqlQuery sql) throws SQLException {
		DbManager man = null;
		try {
			man = new DbManager();
			return executeInsert(sql, man);
		} finally {
			if (man != null) {
				man.close();
			}
		}
	}

	public static Object executeInsert(SqlQuery sql, DbManager man) throws SQLException {
		boolean closeMan = false;

		try {
			if (man == null) {
				man = new DbManager();
				closeMan = true;
			}
			return man.insert(sql);
		} finally {
			if (closeMan) {
				man.close();
			}
		}
	}

	public static int executeUpdate(SqlQuery sql) throws SQLException {
		DbManager man = null;
		try {
			man = new DbManager();
			return executeUpdate(sql, man);
		} finally {
			if (man != null) {
				man.close();
			}
		}
	}

	public static int executeUpdate(SqlQuery sql, DbManager man) throws SQLException {
		boolean closeMan = false;

		try {
			if (man == null) {
				man = new DbManager();
				closeMan = true;
			}
			return man.update(sql);
		} finally {
			if (closeMan) {
				man.close();
			}
		}
	}

	public static int executeDelete(SqlQuery sql) throws SQLException {
		DbManager man = null;
		try {
			man = new DbManager();
			return executeDelete(sql, man);
		} finally {
			if (man != null) {
				man.close();
			}
		}
	}

	public static int executeDelete(SqlQuery sql, DbManager man) throws SQLException {
		boolean closeMan = false;

		try {
			if (man == null) {
				man = new DbManager();
				closeMan = true;
			}
			return man.delete(sql);
		} finally {
			if (closeMan) {
				man.close();
			}
		}
	}

	public static <T> T executeSelectSingle(SqlQuery sql, IEntityFactory<T> factory) throws SQLException {
		DbManager man = null;
		try {
			man = new DbManager();
			return executeSelectSingle(sql, factory, man);
		} finally {
			if (man != null) {
				man.close();
			}
		}
	}

	public static <T> T executeSelectSingle(SqlQuery sql, IEntityFactory<T> factory, DbManager man) throws SQLException {
		boolean closeMan = false;

		ResultSet rs = null;
		try {
			if (man == null) {
				man = new DbManager();
				closeMan = true;
			}

			rs = man.select(sql);

			if (rs.next())
				return factory.create(rs);
		} finally {
			if (closeMan) {
				man.close(rs);
			}
			else if (rs != null) {
				rs.close();
			}
		}
		return null;
	}

	public static <T> List<T> executeSelectList(SqlQuery sql, IEntityFactory<T> factory) throws SQLException {
		DbManager man = null;
		try {
			man = new DbManager();
			return executeSelectList(sql, factory, man);
		} finally {
			if (man != null) {
				man.close();
			}
		}
	}

	public static <T> List<T> executeSelectList(SqlQuery sql, IEntityFactory<T> factory, DbManager man) throws SQLException {
		List<T> list = new ArrayList<T>();

		boolean closeMan = false;

		ResultSet rs = null;
		try {
			if (man == null) {
				man = new DbManager();
				closeMan = true;
			}

			rs = man.select(sql);

			while (rs.next())
				list.add(factory.create(rs));
		} finally {
			if (closeMan) {
				man.close(rs);
			}
			else if (rs != null) {
				rs.close();
			}
		}

		return list;
	}

	public static Object executeScalar(SqlQuery sql) throws SQLException {
		DbManager man = null;
		try {
			man = new DbManager();
			return executeScalar(sql, man);
		} finally {
			if (man != null) {
				man.close();
			}
		}
	}

	public static Object executeScalar(SqlQuery sql, DbManager man) throws SQLException {
		boolean closeMan = false;

		ResultSet rs = null;
		try {
			if (man == null) {
				man = new DbManager();
				closeMan = true;
			}

			rs = man.select(sql);

			if (rs.next())
				return rs.getObject(1);
		} finally {
			if (closeMan) {
				man.close(rs);
			}
			else if (rs != null) {
				rs.close();
			}
		}

		return null;
	}
}