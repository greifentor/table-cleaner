package de.ollie.tablecleaner.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class DeleteOperationModel implements OperationModel {

	private String tableName;

}