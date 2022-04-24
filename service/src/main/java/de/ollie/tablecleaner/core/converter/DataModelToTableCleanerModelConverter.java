package de.ollie.tablecleaner.core.converter;

import java.util.List;
import java.util.stream.Stream;

import javax.inject.Named;

import archimedes.model.ColumnModel;
import archimedes.model.DataModel;
import de.ollie.tablecleaner.core.model.ColumnInfo;
import de.ollie.tablecleaner.core.model.TableCleanerModel;
import de.ollie.tablecleaner.core.model.TableInfo;

@Named
public class DataModelToTableCleanerModelConverter {

	public TableCleanerModel convert(DataModel dataModel) {
		TableCleanerModel tableCleanerModel = new TableCleanerModel();
		Stream
				.of(dataModel.getTables())
				.forEach(table -> tableCleanerModel.addTableInfo(new TableInfo().setName(table.getName())));
		streamOfAllColumnsReferencingTable(dataModel)
				.forEach(
						column -> tableCleanerModel
								.addReferencingColumnInfo(
										column.getReferencedTable().getName(),
										columnModelToColumnInfo(column)));
		return tableCleanerModel;
	}

	private Stream<ColumnModel> streamOfAllColumnsReferencingTable(DataModel dataModel) {
		return List.of(dataModel.getAllColumns()).stream().filter(column -> column.getReferencedTable() != null);
	}

	private ColumnInfo columnModelToColumnInfo(ColumnModel column) {
		return new ColumnInfo()
				.setName(column.getName())
				.setTableName(column.getTable().getName())
				.setNullable(!column.isNotNull());
	}

}