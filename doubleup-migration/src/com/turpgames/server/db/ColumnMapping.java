package com.turpgames.server.db;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import com.turpgames.server.db.repository.Db;

public class ColumnMapping {
	private final PropertyDescriptor propDesc;
	private final String columnName;
	private final int sqlType;
	private boolean isIdentity;

	public ColumnMapping(PropertyDescriptor propDesc) {
		this.propDesc = propDesc;
		this.columnName = toColumnName(getPropertyName());
		this.sqlType = Db.getSqlType(getType());
	}

	public String getColumnName() {
		return columnName;
	}

	public String getPropertyName() {
		return propDesc.getName();
	}

	public Class<?> getType() {
		return propDesc.getPropertyType();
	}

	public int getSqlType() {
		return sqlType;
	}

	public Method getGetMethod() {
		return propDesc.getReadMethod();
	}

	public Method getSetMethod() {
		return propDesc.getWriteMethod();
	}

	public boolean isIdentity() {
		return isIdentity;
	}

	public void setIdentity(boolean isIdentity) {
		this.isIdentity = isIdentity;
	}

	private static String toColumnName(String propName) {
		String colName = "";
		for (int i = 0; i < propName.length(); i++) {
			char c = propName.charAt(i);
			if (Character.isUpperCase(c)) {
				colName += "_";
			}
			colName += Character.toLowerCase(c);
		}
		return colName;
	}

}
