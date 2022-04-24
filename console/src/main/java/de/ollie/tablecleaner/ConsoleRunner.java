package de.ollie.tablecleaner;

import java.io.PrintStream;
import java.util.List;

import org.springframework.boot.ApplicationArguments;

import archimedes.legacy.scheme.ArchimedesObjectFactory;
import archimedes.model.DataModel;
import archimedes.scheme.xml.ModelXMLReader;
import de.ollie.tablecleaner.commands.createcleanscript.CleanScriptCreateCommand;
import de.ollie.tablecleaner.core.converter.DataModelToTableCleanerModelConverter;
import de.ollie.tablecleaner.core.service.ScriptWriter;
import de.ollie.tablecleaner.core.service.TableCleanerScriptGenerator;
import lombok.RequiredArgsConstructor;

/**
 * @author ollie (23.04.2022)
 */
@RequiredArgsConstructor
public class ConsoleRunner {

	public static final String FILE_OPTION_NAME = "file";

	private final DataModelToTableCleanerModelConverter dataModelToTableCleanerModelConverter;
	private final PrintStream out;
	private final TableCleanerScriptGenerator tableCleanerScriptGenerator;
	private final ScriptWriter scriptWriter;

	public void run(ApplicationArguments args) {
		List<String> fileNames = args.getOptionValues(FILE_OPTION_NAME);
		try {
		new CleanScriptCreateCommand(
				readDataModelFromFile(fileNames.get(0)),
				dataModelToTableCleanerModelConverter,
				out,
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