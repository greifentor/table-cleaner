package de.ollie.tablecleaner;

import javax.inject.Inject;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import de.ollie.tablecleaner.core.converter.DataModelToTableCleanerModelConverter;
import de.ollie.tablecleaner.core.service.ScriptWriter;
import de.ollie.tablecleaner.core.service.TableCleanerScriptGenerator;

@SpringBootApplication
@ComponentScan("de.ollie")
public class TableCleanerCLI implements ApplicationRunner {

	@Inject
	private DataModelToTableCleanerModelConverter dataModelToTableCleanerModelConverter;
	@Inject
	private TableCleanerScriptGenerator tableCleanerScriptGenerator;
	@Inject
	private ScriptWriter scriptWriter;

	public static void main(String[] args) {
		SpringApplication.run(TableCleanerCLI.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		new ConsoleRunner(dataModelToTableCleanerModelConverter, System.out, tableCleanerScriptGenerator, scriptWriter)
				.run(args);
	}

}