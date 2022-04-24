package de.ollie.tablecleaner.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SetNullOperationModel implements OperationModel {

	private String columnName;
	private String tableName;

}