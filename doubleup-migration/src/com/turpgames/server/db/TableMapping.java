package com.turpgames.server.db;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import com.turpgames.utils.Util;

public class TableMapping<T> {
	private final Class<T> entityClass;
	private final String tableName;
	private final ColumnMapping[] columnMappings;
	private final ColumnMapping idColumnMapping;

	public TableMapping(Class<T> entityClass, String tableName, String idColumn) {
		this.entityClass = entityClass;
		this.tableName = tableName;

		List<ColumnMapping> colMappingsList = initColumnMappings();

		this.columnMappings = colMappingsList.toArray(new ColumnMapping[0]);

		if (!Util.Strings.isNullOrWhitespace(idColumn)) {
			for (ColumnMapping cm : columnMappings) {
				if (idColumn.equals(cm.getPropertyName())) {
					this.idColumnMapping = cm;
					this.idColumnMapping.setIdentity(true);
					return;
				}
			}
		}
		
		this.idColumnMapping = null;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public String getTableName() {
		return tableName;
	}

	public ColumnMapping[] getColumnMappings() {
		return columnMappings;
	}

	public ColumnMapping getIdColumnMapping() {
		return idColumnMapping;
	}

	public ColumnMapping getColumnMapping(String propertyName) {
		for (ColumnMapping cm : columnMappings) {
			if (cm.getPropertyName().equals(propertyName)) {
				return cm;
			}
		}
		return null;
	}

	private List<ColumnMapping> initColumnMappings() {
		List<ColumnMapping> colMappingsList = new ArrayList<ColumnMapping>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(entityClass);
			for (PropertyDescriptor desc : beanInfo.getPropertyDescriptors()) {
				if (desc.getWriteMethod() != null && desc.getReadMethod() != null)
					colMappingsList.add(new ColumnMapping(desc));
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return colMappingsList;
	}
}
