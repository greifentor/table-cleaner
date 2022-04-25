package de.ollie.tablecleaner.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.tablecleaner.core.model.DeleteOperationModel;
import de.ollie.tablecleaner.core.model.ScriptModel;
import de.ollie.tablecleaner.core.model.SetNullOperationModel;
import de.ollie.tablecleaner.core.model.TableCleanerConfiguration;
import de.ollie.tablecleaner.core.service.ScriptWriter;

@ExtendWith(MockitoExtension.class)
class ScriptWriterImplTest {

	private static final String COLUMN_NAME = "ColumnName";
	private static final String TABLE_NAME_1 = "TableName1";
	private static final String TABLE_NAME_2 = "TableName2";

	@InjectMocks
	private ScriptWriterImpl unitUnderTest;

	@Nested
	class TestOfMethod_createScript_ScriptModel_PrintStream {

		@Test
		void passAScriptModel_writesACorrectOutputStream() throws Exception {
			// Prepare
			ScriptModel scriptModel =
					new ScriptModel()
							.addOperation(new DeleteOperationModel().setTableName(TABLE_NAME_1))
							.addOperation(
									new SetNullOperationModel().setColumnName(COLUMN_NAME).setTableName(TABLE_NAME_2));
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			TableCleanerConfiguration tableCleanerConfiguration =
					new TableCleanerConfiguration()
							.setDeleteOperationPattern("DELETE FROM " + ScriptWriter.TABLE_NAME_PLACE_HOLDER + ";")
							.setOutputStream(out)
							.setSetNullOperationPattern(
									"UPDATE " + ScriptWriter.TABLE_NAME_PLACE_HOLDER + " SET "
											+ ScriptWriter.COLUMN_NAME_PLACE_HOLDER + " = NULL;");
			// Run
			unitUnderTest.createScript(scriptModel, tableCleanerConfiguration);
			// Check
			assertEquals(
					"DELETE FROM " + TABLE_NAME_1 + ";\nUPDATE " + TABLE_NAME_2 + " SET " + COLUMN_NAME + " = NULL;\n",
					out.toString());
		}

	}

}
