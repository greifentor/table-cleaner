package de.ollie.tablecleaner;

import java.io.PrintStream;
import java.util.List;

import org.springframework.boot.ApplicationArguments;

import archimedes.legacy.scheme.ArchimedesObjectFactory;
import archimedes.model.DataModel;
import archimedes.scheme.xml.ModelXMLReader;
import de.ollie.tablecleaner.commands.createcleanscript.CleanScriptCreateCommand;
import de.ollie.tablecleaner.core.converter.DataModelToTableCleanerModelConverter;
import de.ollie.tablecleaner.core.model.TableCleanerConfiguration;
import de.ollie.tablecleaner.core.service.ScriptWriter;
import de.ollie.tablecleaner.core.service.TableCleanerScriptGenerator;
import lombok.RequiredArgsConstructor;

/**
 * @author ollie (23.04.2022)
 */
@RequiredArgsConstructor
public class ConsoleRunner {

	public static final String DELETE_OPERATION_PATTERN_OPTION_NAME = "deleteOperationPattern";
	public static final String FILE_OPTION_NAME = "file";
	public static final String SET_NULL_OPERATION_PATTERN_OPTION_NAME = "setNullOperationPattern";

	private final DataModelToTableCleanerModelConverter dataModelToTableCleanerModelConverter;
	private final PrintStream out;
	private final TableCleanerScriptGenerator tableCleanerScriptGenerator;
	private final ScriptWriter scriptWriter;

	public void run(ApplicationArguments args) {
		String deleteOperationPattern =
				args.containsOption(DELETE_OPERATION_PATTERN_OPTION_NAME)
						? args.getOptionValues(DELETE_OPERATION_PATTERN_OPTION_NAME).get(0)
						: "DELETE FROM " + ScriptWriter.TABLE_NAME_PLACE_HOLDER + ";";
		List<String> fileNames = args.getOptionValues(FILE_OPTION_NAME);
		String setNullOperationPattern =
				args.containsOption(SET_NULL_OPERATION_PATTERN_OPTION_NAME)
						? args.getOptionValues(SET_NULL_OPERATION_PATTERN_OPTION_NAME).get(0)
						: "UPDATE " + ScriptWriter.TABLE_NAME_PLACE_HOLDER + " SET "
								+ ScriptWriter.COLUMN_NAME_PLACE_HOLDER + " = NULL;";
		try {
		new CleanScriptCreateCommand(
				new TableCleanerConfiguration()
						.setDataModel(readDataModelFromFile(fileNames.get(0)))
						.setDeleteOperationPattern(deleteOperationPattern)
						.setOutputStream(out)
						.setSetNullOperationPattern(setNullOperationPattern),
				dataModelToTableCleanerModelConverter,
				tableCleanerScriptGenerator,
				scriptWriter).createScript();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DataModel readDataModelFromFile(String fileName) {
		ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
		return reader.read(fileName);
	}

}