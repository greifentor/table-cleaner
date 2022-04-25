package de.ollie.tablecleaner.core.service.impl;

import java.io.IOException;

import javax.inject.Named;

import de.ollie.tablecleaner.core.model.DeleteOperationModel;
import de.ollie.tablecleaner.core.model.OperationModel;
import de.ollie.tablecleaner.core.model.ScriptModel;
import de.ollie.tablecleaner.core.model.SetNullOperationModel;
import de.ollie.tablecleaner.core.model.TableCleanerConfiguration;
import de.ollie.tablecleaner.core.service.ScriptWriter;

@Named
public class ScriptWriterImpl implements ScriptWriter {

	@Override
	public void createScript(ScriptModel scriptModel, TableCleanerConfiguration configuration) throws IOException {
		for (OperationModel operation : scriptModel.getOperations()) {
			writeOperation(operation, configuration);
		}
	}

	private void writeOperation(OperationModel operation, TableCleanerConfiguration configuration) throws IOException {
		if (operation instanceof DeleteOperationModel) {
			String deleteOperationString =
					configuration
							.getDeleteOperationPattern()
							.replace(TABLE_NAME_PLACE_HOLDER, operation.getTableName());
			configuration.getOutputStream().write((deleteOperationString + "\n").getBytes());
		} else if (operation instanceof SetNullOperationModel) {
			SetNullOperationModel snom = (SetNullOperationModel) operation;
			String setNullOperationString =
					configuration
							.getSetNullOperationPattern()
							.replace(TABLE_NAME_PLACE_HOLDER, snom.getTableName())
							.replace(COLUMN_NAME_PLACE_HOLDER, snom.getColumnName());
			configuration.getOutputStream().write((setNullOperationString + "\n").getBytes());
		}
	}

}