package de.ollie.tablecleaner.core.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ScriptModel {

	private List<OperationModel> operations = new ArrayList<>();

	public ScriptModel addOperation(OperationModel operationModel) {
		operations.add(operationModel);
		return this;
	}

}