package de.ollie.tablecleaner.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class SetNullOperationModel implements OperationModel {

	private String columnName;
	private String tableName;

}