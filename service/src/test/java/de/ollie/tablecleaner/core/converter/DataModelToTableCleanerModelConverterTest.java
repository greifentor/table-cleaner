package de.ollie.tablecleaner.core.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import archimedes.legacy.scheme.ArchimedesObjectFactory;
import archimedes.model.DataModel;
import archimedes.model.TableModel;
import archimedes.scheme.xml.ModelXMLReader;
import de.ollie.tablecleaner.core.model.TableCleanerModel;

@ExtendWith(MockitoExtension.class)
class DataModelToTableCleanerModelConverterTest {

	private DataModel dataModel;

	@InjectMocks
	private DataModelToTableCleanerModelConverter unitUnderTest;

	@BeforeEach
	void setUp() {
		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
		dataModel = reader.read("src/test/resources/models/Converter-Test.xml");
	}

	@Nested
	class TestsOfMethod_convert_DataModel {

		private TableCleanerModel tableCleanerModel;

		@Nested
		class cleanRun {

			@BeforeEach
			void setUp() {
				tableCleanerModel = unitUnderTest.convert(dataModel);
			}

			@Test
			void containsAllTableFromDataModel() {
				for (TableModel table : dataModel.getTables()) {
					assertEquals(table.getName(), tableCleanerModel.getTableInfoByName(table.getName()).getName());
				}
			}

			@Test
			void aTableContainsTheCorrectNullableColumnRefernce() {
				assertNotNull(
						tableCleanerModel
								.getTableInfoByName("A_TABLE")
								.getReferencingColumnInfoByName("TABLE_NULLABLE_REF", "NULLABLE_REF"));
			}

			@Test
			void nullableColumnRefernceIsNullable() {
				assertTrue(
						tableCleanerModel
								.getTableInfoByName("A_TABLE")
								.getReferencingColumnInfoByName("TABLE_NULLABLE_REF", "NULLABLE_REF")
								.isNullable());
			}

			@Test
			void aTableContainsTheCorrectNotNullableColumnRefernce() {
				assertNotNull(
						tableCleanerModel
								.getTableInfoByName("A_TABLE")
								.getReferencingColumnInfoByName("TABLE_NOT_NULLABLE_REF", "NOT_NULLABLE_REF"));
			}

			@Test
			void notNullableColumnRefernceIsNullable() {
				assertFalse(
						tableCleanerModel
								.getTableInfoByName("A_TABLE")
								.getReferencingColumnInfoByName("TABLE_NOT_NULLABLE_REF", "NOT_NULLABLE_REF")
								.isNullable());
			}

		}

	}

}