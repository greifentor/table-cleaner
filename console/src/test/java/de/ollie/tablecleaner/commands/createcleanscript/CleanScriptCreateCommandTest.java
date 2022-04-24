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
				dataModel,
				new DataModelToTableCleanerModelConverter(),
				out,
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

}