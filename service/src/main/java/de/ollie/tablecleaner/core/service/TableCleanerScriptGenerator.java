package de.ollie.tablecleaner.core.service;

import de.ollie.tablecleaner.core.model.ScriptModel;
import de.ollie.tablecleaner.core.model.TableCleanerModel;

public interface TableCleanerScriptGenerator {

	ScriptModel generateCleanScriptFor(TableCleanerModel tableCleanerModel);

}