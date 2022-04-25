package de.ollie.tablecleaner.core.service;

import java.io.IOException;

import de.ollie.tablecleaner.core.model.ScriptModel;
import de.ollie.tablecleaner.core.model.TableCleanerConfiguration;

public interface ScriptWriter {

	public static final String COLUMN_NAME_PLACE_HOLDER = "${ColumnName}";
	public static final String TABLE_NAME_PLACE_HOLDER = "${TableName}";

	void createScript(ScriptModel scriptModel, TableCleanerConfiguration configuration) throws IOException;

}
