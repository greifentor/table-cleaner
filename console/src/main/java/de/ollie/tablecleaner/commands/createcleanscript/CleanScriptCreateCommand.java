package de.ollie.tablecleaner.commands.createcleanscript;

import de.ollie.tablecleaner.core.converter.DataModelToTableCleanerModelConverter;
import de.ollie.tablecleaner.core.model.ScriptModel;
import de.ollie.tablecleaner.core.model.TableCleanerConfiguration;
import de.ollie.tablecleaner.core.model.TableCleanerModel;
import de.ollie.tablecleaner.core.service.ScriptWriter;
import de.ollie.tablecleaner.core.service.TableCleanerScriptGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CleanScriptCreateCommand {

	private final TableCleanerConfiguration tableCleanerConfiguration;
	private final DataModelToTableCleanerModelConverter dataModelToTableCleanerModelConverter;
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
		tableCleanerModel = dataModelToTableCleanerModelConverter.convert(tableCleanerConfiguration.getDataModel());
	}

	private void generateCleanScriptForTableCleanerModel() {
		scriptModel = tableCleanerScriptGenerator.generateCleanScriptFor(tableCleanerModel);
	}

	private void writeScript() throws Exception {
		scriptWriter.createScript(scriptModel, tableCleanerConfiguration);
	}

}