package de.ollie.tablecleaner.core.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TableInfo {

	private String name;
	private List<ColumnInfo> referencingColumnInfos = new ArrayList<>();

	public void addReferencingColumnInfo(ColumnInfo referencingColumnInfo) {
		if (getReferencingColumnInfoByName(
				referencingColumnInfo.getTableName(),
				referencingColumnInfo.getName()) == null) {
			referencingColumnInfos.add(referencingColumnInfo);
		}
	}

	public ColumnInfo getReferencingColumnInfoByName(String tableName, String columnName) {
		return referencingColumnInfos
				.stream()
				.filter(
						columnInfo -> columnInfo.getName().equals(columnName)
								&& columnInfo.getTableName().equals(tableName))
				.findFirst()
				.orElse(null);
	}

	public boolean hasColumnInfo(String tableName, String columnName) {
		return getReferencingColumnInfos()
				.stream()
				.anyMatch(
						columnInfo -> columnInfo.getName().equals(columnName)
								&& columnInfo.getTableName().equals(tableName));
	}

	public boolean hasReferences() {
		return !referencingColumnInfos.isEmpty();
	}

	public void removeColumnInfo(String tableName, String columnName) {
		for (int i = referencingColumnInfos.size() - 1; i >= 0; i--) {
			ColumnInfo columnInfo = referencingColumnInfos.get(i);
			if (columnInfo.getName().equals(columnName) && columnInfo.getTableName().equals(tableName)) {
				referencingColumnInfos.remove(i);
				return;
			}
		}
	}

	public void removeAllReferencesFromTable(String tableName) {
		for (int i = referencingColumnInfos.size() - 1; i >= 0; i--) {
			if (referencingColumnInfos.get(i).getTableName().equals(tableName)) {
				referencingColumnInfos.remove(i);
			}
		}
	}

}