package de.ollie.tablecleaner.commands.createcleanscript;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import archimedes.legacy.scheme.ArchimedesObjectFactory;
import archimedes.model.DataModel;
import archimedes.scheme.xml.ModelXMLReader;
import de.ollie.tablecleaner.core.converter.DataModelToTableCleanerModelConverter;
import de.ollie.tablecleaner.core.model.TableCleanerConfiguration;
import de.ollie.tablecleaner.core.service.impl.ScriptWriterImpl;
import de.ollie.tablecleaner.core.service.impl.TableCleanerScriptGeneratorImpl;

@ExtendWith(MockitoExtension.class)
class CleanScriptCreateCommandTest {

	@Test
	void cleanRun() throws Exception {
		// Prepare
		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
		DataModel dataModel = reader.read("src/test/resources/models/Integration-Test.xml");
		OutputStream out = new ByteArrayOutputStream();
		// Run
		new CleanScriptCreateCommand(
				new TableCleanerConfiguration()
						.setDataModel(dataModel)
						.setDeleteOperationPattern("DELETE FROM ${TableName};")
						.setOutputStream(out)
						.setSetNullOperationPattern("UPDATE ${TableName} SET ${ColumnName} = NULL;"),
				new DataModelToTableCleanerModelConverter(),
				new TableCleanerScriptGeneratorImpl(),
				new ScriptWriterImpl()).createScript();
		// Check
		assertEquals("UPDATE TABLE_NULLABLE_REF SET NULLABLE_REF = NULL;\n" + //
				"UPDATE ANOTHER_TABLE_NULLABLE_REF SET NULLABLE_REF = NULL;\n" + //
				"DELETE FROM B_TABLE;\n" + //
				"DELETE FROM TABLE_NOT_NULLABLE_REF;\n" + //
				"DELETE FROM ANOTHER_TABLE_NULLABLE_REF;\n" + //
				"DELETE FROM TABLE_NULLABLE_REF;\n" + //
				"DELETE FROM A_TABLE;\n", out.toString());
	}

	@Test
	void cleanRun_withAlternativeOutput() throws Exception {
		// Prepare
		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
		DataModel dataModel = reader.read("src/test/resources/models/Integration-Test.xml");
		OutputStream out = new ByteArrayOutputStream();
		// Run
		new CleanScriptCreateCommand(
				new TableCleanerConfiguration()
						.setDataModel(dataModel)
						.setDeleteOperationPattern("Blubs ${TableName}")
						.setOutputStream(out)
						.setSetNullOperationPattern("Schnupps ${TableName}.${ColumnName}"),
				new DataModelToTableCleanerModelConverter(),
				new TableCleanerScriptGeneratorImpl(),
				new ScriptWriterImpl()).createScript();
		// Check
		assertEquals("Schnupps TABLE_NULLABLE_REF.NULLABLE_REF\n" + //
				"Schnupps ANOTHER_TABLE_NULLABLE_REF.NULLABLE_REF\n" + //
				"Blubs B_TABLE\n" + //
				"Blubs TABLE_NOT_NULLABLE_REF\n" + //
				"Blubs ANOTHER_TABLE_NULLABLE_REF\n" + //
				"Blubs TABLE_NULLABLE_REF\n" + //
				"Blubs A_TABLE\n", out.toString());
	}

}