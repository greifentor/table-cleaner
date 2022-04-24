package de.ollie.tablecleaner.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import de.ollie.tablecleaner.core.model.ColumnInfo;
import de.ollie.tablecleaner.core.model.DeleteOperationModel;
import de.ollie.tablecleaner.core.model.ScriptModel;
import de.ollie.tablecleaner.core.model.SetNullOperationModel;
import de.ollie.tablecleaner.core.model.TableCleanerModel;
import de.ollie.tablecleaner.core.model.TableInfo;
import de.ollie.tablecleaner.core.service.TableCleanerScriptGenerator;

@Named
public class TableCleanerScriptGeneratorImpl implements TableCleanerScriptGenerator {

	@Override
	public ScriptModel generateCleanScriptFor(TableCleanerModel tableCleanerModel) {
		ScriptModel script = new ScriptModel();
		List<ColumnInfo> columnInfos = new ArrayList<>();
		tableCleanerModel
				.getTableInfoStream()
				.forEach(
						tableInfo -> tableInfo
								.getReferencingColumnInfos()
								.stream()
								.filter(columnInfo -> columnInfo.isNullable())
								.forEach(columnInfo -> {
									script
											.addOperation(
													new SetNullOperationModel()
															.setColumnName(columnInfo.getName())
															.setTableName(columnInfo.getTableName()));
									columnInfos.add(columnInfo);
								}));
		tableCleanerModel.cleanUpColumnInfos(columnInfos);
		long oldTableCount = 0;
		while (oldTableCount != tableCleanerModel.getTableInfoCount()) {
			List<String> tableNames = new ArrayList<>();
			oldTableCount = tableCleanerModel.getTableInfoCount();
			tableCleanerModel
					.getTableInfoStream()
					.filter(tableInfo -> !tableInfo.hasReferences())
					.forEach(tableInfo -> {
						script.addOperation(new DeleteOperationModel().setTableName(tableInfo.getName()));
						tableNames.add(tableInfo.getName());
					});
			tableNames.forEach(tableName -> tableCleanerModel.removeTableInfo(tableName));
		}
		if (tableCleanerModel.getTableInfoCount() > 0) {
			throw new IllegalStateException(
					"there are unprocessed tables: " + tableCleanerModel
							.getTableInfoStream()
							.map(TableInfo::getName)
							.reduce((s0, s1) -> s0 + ", " + s1)
							.orElse("-"));
		}
		return script;
	}

}