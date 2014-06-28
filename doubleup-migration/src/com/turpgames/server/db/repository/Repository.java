package com.turpgames.server.db.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.turpgames.server.db.ColumnMapping;
import com.turpgames.server.db.DbManager;
import com.turpgames.server.db.IEntityFactory;
import com.turpgames.server.db.SqlQuery;
import com.turpgames.server.db.TableMapping;

public class Repository<T> {
	private final TableMapping<T> tableMapping;
	private final IEntityFactory<T> factory;

	public Repository(Class<T> entityClass, String tableName, String identityColumn) {
		tableMapping = new TableMapping<T>(entityClass, tableName, identityColumn);
		factory = new IEntityFactory<T>() {
			@Override
			public T create(ResultSet rs) throws SQLException {
				try {
					T t = tableMapping.getEntityClass().newInstance();
					for (ColumnMapping colMap : tableMapping.getColumnMappings())
						colMap.getSetMethod().invoke(t, rs.getObject(colMap.getColumnName()));
					return t;
				} catch (Exception e) {
					throw new SQLException(e);
				}
			}
		};
	}

	public void insert(T entity) throws RepositoryException {
		insert(entity, null);
	}
	
	public void insert(T entity, DbManager db) throws RepositoryException {
		try {
			SqlQuery sql = buildInsertSql(entity);
			Object id = DbManager.executeInsert(sql, db);

			if (id == null)
				return;

			ColumnMapping idColMap = tableMapping.getIdColumnMapping();

			if (idColMap == null)
				return;

			idColMap.getSetMethod().invoke(entity, id);
		} catch (Throwable t) {
			throw new RepositoryException(t);
		}
	}

	public void update(T entity) throws RepositoryException {
		update(entity, null);
	}

	public void update(T entity, DbManager db) throws RepositoryException {
		if (tableMapping.getIdColumnMapping() == null)
			throw new RepositoryException("%s does not contain id column", tableMapping.getTableName());

		try {
			SqlQuery sql = buildUpdateSql(entity);
			DbManager.executeUpdate(sql, db);
		} catch (Throwable t) {
			throw new RepositoryException(t);
		}
	}

	public void delete(T entity) throws RepositoryException {
		delete(entity, null);
	}

	public void delete(Object id, DbManager db) throws RepositoryException {
		if (tableMapping.getIdColumnMapping() == null)
			throw new RepositoryException("%s does not contain id column", tableMapping.getTableName());

		try {
			SqlQuery sql = buildDeleteSql(id);
			DbManager.executeDelete(sql, db);
		} catch (Throwable t) {
			throw new RepositoryException(t);
		}
	}

	public T selectById(Object id) throws RepositoryException {
		return selectById(id, null);
	}

	public T selectById(Object id, DbManager db) throws RepositoryException {
		if (tableMapping.getIdColumnMapping() == null)
			throw new RepositoryException("%s does not contain id column", tableMapping.getTableName());

		try {
			SqlQuery sql = buildSelectByIdSql(id);
			List<T> list = select(sql, db);

			return list.size() > 0 ? list.get(0) : null;
		} catch (Throwable t) {
			throw new RepositoryException(t);
		}
	}

	public List<T> selectAll() throws RepositoryException {
		return selectAll(null);
	}
	
	public List<T> selectAll(DbManager db) throws RepositoryException {
		try {
			SqlQuery sql = buildSelectAllSql();
			return select(sql, db);
		} catch (Throwable t) {
			throw new RepositoryException(t);
		}
	}
	
	public List<T> selectWhere(SqlQuery whereSql) throws RepositoryException {
		return selectWhere(whereSql, null);
	}

	public List<T> selectWhere(SqlQuery whereSql, DbManager db) throws RepositoryException {
		try {
			SqlQuery selectSql = new SqlQuery("select * from ")
					.append(tableMapping.getTableName())
					.append(" where ")
					.append(whereSql.getQuery());
			selectSql.addParameters(whereSql.getParameters());
			return select(selectSql, db);
		} catch (Throwable t) {
			throw new RepositoryException(t);
		}
	}
	
	public List<T> select(SqlQuery sql) throws RepositoryException {
		return select(sql, null);
	}

	public List<T> select(SqlQuery sql, DbManager db) throws RepositoryException {
		try {
			return DbManager.executeSelectList(sql, factory, db);
		} catch (Throwable t) {
			throw new RepositoryException(t);
		}
	}

	private SqlQuery buildInsertSql(T entity) throws Exception {
		SqlQuery sql = new SqlQuery();

		sql.append("insert into ").append(tableMapping.getTableName()).append(" (");

		StringBuffer values = new StringBuffer();

		String prefix = "";
		for (ColumnMapping colMap : tableMapping.getColumnMappings()) {
			if (colMap.isIdentity())
				continue;
			sql.append(prefix)
					.append(colMap.getColumnName())
					.addParameter(colMap.getGetMethod().invoke(entity), colMap.getSqlType());
			values.append(prefix).append("?");
			prefix = ",";
		}

		sql.append(") values (").append(values.toString()).append(")");

		return sql;
	}

	private SqlQuery buildUpdateSql(T entity) throws Exception {
		SqlQuery sql = new SqlQuery();

		sql.append("update ")
				.append(tableMapping.getTableName())
				.append(" set ");

		String prefix = "";
		for (ColumnMapping colMap : tableMapping.getColumnMappings()) {
			if (colMap.isIdentity())
				continue;
			sql.append(prefix)
					.append(colMap.getColumnName())
					.append(" = ?")
					.addParameter(colMap.getGetMethod().invoke(entity), colMap.getSqlType());
			prefix = ",";
		}

		ColumnMapping idColMap = tableMapping.getIdColumnMapping();

		sql.append(" where ")
				.append(idColMap.getColumnName())
				.append(" = ?")
				.addParameter(idColMap.getGetMethod().invoke(entity), idColMap.getSqlType());

		return sql;
	}

	private SqlQuery buildDeleteSql(Object id) {
		ColumnMapping idColMap = tableMapping.getIdColumnMapping();

		SqlQuery sql = new SqlQuery();

		sql.append("delete from ")
				.append(tableMapping.getTableName())
				.append(" where ")
				.append(idColMap.getColumnName())
				.append(" = ?")
				.addParameter(id, idColMap.getSqlType());

		return sql;
	}

	private SqlQuery buildSelectByIdSql(Object id) {
		SqlQuery sql = new SqlQuery();

		sql.append("select * from ")
				.append(tableMapping.getTableName())
				.append(" where ")
				.append(tableMapping.getIdColumnMapping().getColumnName())
				.append(" = ?")
				.addParameter(id, tableMapping.getIdColumnMapping().getSqlType());

		return sql;
	}

	private SqlQuery buildSelectAllSql() {
		SqlQuery sql = new SqlQuery();

		sql.append("select * from ").append(tableMapping.getTableName());

		return sql;
	}
}
