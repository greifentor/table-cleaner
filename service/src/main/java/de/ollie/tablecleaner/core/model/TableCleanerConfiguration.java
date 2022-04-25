package de.ollie.tablecleaner.core.model;

import java.io.OutputStream;

import archimedes.model.DataModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class TableCleanerConfiguration {

	private DataModel dataModel;
	private String deleteOperationPattern;
	private OutputStream outputStream;
	private String setNullOperationPattern;

}