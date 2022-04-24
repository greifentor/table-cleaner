package de.ollie.tablecleaner.core.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ColumnInfo {

	private String name;
	private String tableName;
	private boolean nullable;

}