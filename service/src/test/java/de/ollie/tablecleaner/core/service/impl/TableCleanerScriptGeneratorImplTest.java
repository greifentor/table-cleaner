package de.ollie.tablecleaner.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.tablecleaner.core.model.ColumnInfo;
import de.ollie.tablecleaner.core.model.DeleteOperationModel;
import de.ollie.tablecleaner.core.model.ScriptModel;
import de.ollie.tablecleaner.core.model.SetNullOperationModel;
import de.ollie.tablecleaner.core.model.TableCleanerModel;
import de.ollie.tablecleaner.core.model.TableInfo;

@ExtendWith(MockitoExtension.class)
public class TableCleanerScriptGeneratorImplTest {

	private static final String COLUMN_NAME_1 = "COLUMN_1";
	private static final String TABLE_NAME_1 = "TABLE_1";
	private static final String TABLE_NAME_2 = "TABLE_2";

	@InjectMocks
	private TableCleanerScriptGeneratorImpl unitUnderTest;

	@Nested
	class TestsOfMethod_generateCleanScriptFor_TableCleanerModel {

		@Nested
		class PassModelWithATable_noReferences {

			private TableCleanerModel createTableCleanerModel() {
				TableCleanerModel tableCleanerModel = new TableCleanerModel();
				tableCleanerModel.addTableInfo(new TableInfo().setName(TABLE_NAME_1));
				return tableCleanerModel;
			}

			@Test
			void returnsAScriptModelWithADeleteOperation() {
				// Prepare & Run
				ScriptModel returned = unitUnderTest.generateCleanScriptFor(createTableCleanerModel());
				// Check
				assertTrue(returned.getOperations().get(0) instanceof DeleteOperationModel);
			}

			@Test
			void returnsAScriptModelWithADeleteOperationWithCorrectTableName() {
				// Prepare & Run
				ScriptModel returned = unitUnderTest.generateCleanScriptFor(createTableCleanerModel());
				// Check
				assertEquals(TABLE_NAME_1, returned.getOperations().get(0).getTableName());
			}

			@Test
			void removesTableFromTableCleanerModel() {
				// Prepare & Run
				TableCleanerModel tableCleanerModel = createTableCleanerModel();
				unitUnderTest.generateCleanScriptFor(tableCleanerModel);
				// Check
				assertEquals(0L, tableCleanerModel.getTableInfoStream().count());
			}

		}

		@Nested
		class PassModelWithATable_noNullableReference {

			private TableCleanerModel createTableCleanerModel() {
				TableCleanerModel tableCleanerModel = new TableCleanerModel();
				tableCleanerModel
						.addTableInfo(
								new TableInfo()
										.setName(TABLE_NAME_2)
										.setReferencingColumnInfos(
												new ArrayList<>(
														List
														.of(
																new ColumnInfo()
																		.setName(COLUMN_NAME_1)
																		.setTableName(TABLE_NAME_1)
																				.setNullable(true)))));
				return tableCleanerModel;
			}

			@Test
			void returnsAScriptModelWithASetNullOperationOnPosition0() {
				// Prepare & Run
				ScriptModel returned = unitUnderTest.generateCleanScriptFor(createTableCleanerModel());
				// Check
				assertTrue(returned.getOperations().get(0) instanceof SetNullOperationModel);
			}

			@Test
			void returnsAScriptModelWithADeleteOperationOnPosition1() {
				// Prepare & Run
				ScriptModel returned = unitUnderTest.generateCleanScriptFor(createTableCleanerModel());
				// Check
				assertTrue(returned.getOperations().get(1) instanceof DeleteOperationModel);
			}

			@Test
			void returnsAScriptModelWithASetNullOperationWithCorrectTableNameOnPosition0() {
				// Prepare & Run
				ScriptModel returned = unitUnderTest.generateCleanScriptFor(createTableCleanerModel());
				// Check
				assertEquals(TABLE_NAME_1, returned.getOperations().get(0).getTableName());
			}

			@Test
			void returnsAScriptModelWithASetNullOperationWithCorrectColumnNamePosition0() {
				// Prepare & Run
				ScriptModel returned = unitUnderTest.generateCleanScriptFor(createTableCleanerModel());
				// Check
				assertEquals(COLUMN_NAME_1, ((SetNullOperationModel) returned.getOperations().get(0)).getColumnName());
			}

			@Test
			void returnsAScriptModelWithADeleteOperationWithCorrectTableNameOnPosition1() {
				// Prepare & Run
				ScriptModel returned = unitUnderTest.generateCleanScriptFor(createTableCleanerModel());
				// Check
				assertEquals(TABLE_NAME_2, returned.getOperations().get(1).getTableName());
			}

		}

	}

}