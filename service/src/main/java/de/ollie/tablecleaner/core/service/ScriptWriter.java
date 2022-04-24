package de.ollie.tablecleaner.core.service;

import java.io.IOException;
import java.io.OutputStream;

import de.ollie.tablecleaner.core.model.ScriptModel;

public interface ScriptWriter {

	void createScript(ScriptModel scriptModel, OutputStream out) throws IOException;

}
