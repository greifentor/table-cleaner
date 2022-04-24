package de.ollie.tablecleaner.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import lombok.ToString;

@ToString
public class TableCleanerModel {

	private Map<String, TableInfo> tableInfos = new HashMap<>();

	public void addReferencingColumnInfo(String tableName, ColumnInfo referencingColumnInfo) {
		TableInfo tableInfo = getTableInfoByName(tableName);
		if (tableInfo == null) {
			tableInfo = new TableInfo().setName(tableName);
			tableInfos.put(tableName, tableInfo);
		}
		tableInfo.addReferencingColumnInfo(referencingColumnInfo);
	}

	public TableInfo addTableInfo(TableInfo tableInfo) {
		String tableName = tableInfo.getName();
		if (getTableInfoByName(tableName) == null) {
			tableInfos.put(tableName, tableInfo);
		}
		return tableInfo;
	}

	public void cleanUpColumnInfos(List<ColumnInfo> columnInfosToRemove) {
		columnInfosToRemove
				.forEach(
						columnInfo -> getTableInfoStream()
								.filter(
										tableInfo -> tableInfo
												.hasColumnInfo(columnInfo.getTableName(), columnInfo.getName()))
								.forEach(
										tableInfo -> tableInfo
												.removeColumnInfo(columnInfo.getTableName(), columnInfo.getName())));
	}

	public TableInfo getTableInfoByName(String name) {
		return tableInfos.getOrDefault(name, null);
	}

	public long getTableInfoCount() {
		return getTableInfoStream().count();
	}

	public Stream<TableInfo> getTableInfoStream() {
		return tableInfos.entrySet().stream().map(Entry::getValue);
	}

	public void removeTableInfo(String tableName) {
		tableInfos.remove(tableName);
		getTableInfoStream().forEach(tableInfo -> tableInfo.removeAllReferencesFromTable(tableName));
	}

}