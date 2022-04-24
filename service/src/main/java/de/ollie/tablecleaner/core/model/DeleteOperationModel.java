package de.ollie.tablecleaner.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class DeleteOperationModel implements OperationModel {

	private String tableName;

}