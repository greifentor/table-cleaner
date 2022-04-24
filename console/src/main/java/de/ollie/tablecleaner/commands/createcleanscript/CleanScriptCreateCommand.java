package de.ollie.tablecleaner.commands.createcleanscript;

import java.io.OutputStream;

import archimedes.model.DataModel;
import de.ollie.tablecleaner.core.converter.DataModelToTableCleanerModelConverter;
import de.ollie.tablecleaner.core.model.ScriptModel;
import de.ollie.tablecleaner.core.model.TableCleanerModel;
import de.ollie.tablecleaner.core.service.ScriptWriter;
import de.ollie.tablecleaner.core.service.TableCleanerScriptGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CleanScriptCreateCommand {

	private final DataModel dataModel;
	private final DataModelToTableCleanerModelConverter dataModelToTableCleanerModelConverter;
	private final OutputStream out;
	private final TableCleanerScriptGenerator tableCleanerScriptGenerator;
	private final ScriptWriter scriptWriter;

	private ScriptModel scriptModel;
	private TableCleanerModel tableCleanerModel;

	public synchronized void createScript() throws Exception {
		convertDataModelToTableCleanerModel();
		generateCleanScriptForTableCleanerModel();
		writeScript();
	}

	private void convertDataModelToTableCleanerModel() {
		tableCleanerModel = dataModelToTableCleanerModelConverter.convert(dataModel);
	}

	private void generateCleanScriptForTableCleanerModel() {
		scriptModel = tableCleanerScriptGenerator.generateCleanScriptFor(tableCleanerModel);
	}

	private void writeScript() throws Exception {
		scriptWriter.createScript(scriptModel, out);
	}

}