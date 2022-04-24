package de.ollie.tablecleaner.core.service.impl;

import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Named;

import de.ollie.tablecleaner.core.model.DeleteOperationModel;
import de.ollie.tablecleaner.core.model.OperationModel;
import de.ollie.tablecleaner.core.model.ScriptModel;
import de.ollie.tablecleaner.core.model.SetNullOperationModel;
import de.ollie.tablecleaner.core.service.ScriptWriter;

@Named
public class ScriptWriterImpl implements ScriptWriter {

	@Override
	public void createScript(ScriptModel scriptModel, OutputStream out) throws IOException {
		for (OperationModel operation : scriptModel.getOperations()) {
			writeOperation(operation, out);
		}
	}

	private void writeOperation(OperationModel operation, OutputStream out) throws IOException {
		if (operation instanceof DeleteOperationModel) {
			out.write(("DELETE FROM " + operation.getTableName() + ";\n").getBytes());
		} else if (operation instanceof SetNullOperationModel) {
			SetNullOperationModel snom = (SetNullOperationModel) operation;
			out.write(("UPDATE " + snom.getTableName() + " SET " + snom.getColumnName() + " = NULL;\n").getBytes());
		}
	}

}