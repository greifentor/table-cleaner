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

	public static final String TABLE_CLEANER_IGNORE = "TABLE_CLEANER_IGNORE";

	public TableCleanerModel convert(DataModel dataModel) {
		TableCleanerModel tableCleanerModel = new TableCleanerModel();
		Stream
				.of(dataModel.getTables())
				.filter(table -> !table.isOptionSet(TABLE_CLEANER_IGNORE))
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
		return List
				.of(dataModel.getAllColumns())
				.stream()
				.filter(column -> isAReference(column) && !isToIgnore(column));
	}

	private boolean isAReference(ColumnModel column) {
		return column.getReferencedTable() != null;
	}

	private boolean isToIgnore(ColumnModel column) {
		return column.getReferencedTable().isOptionSet(TABLE_CLEANER_IGNORE)
				|| column.getTable().isOptionSet(TABLE_CLEANER_IGNORE);
	}

	private ColumnInfo columnModelToColumnInfo(ColumnModel column) {
		return new ColumnInfo()
				.setName(column.getName())
				.setTableName(column.getTable().getName())
				.setNullable(!column.isNotNull());
	}

}