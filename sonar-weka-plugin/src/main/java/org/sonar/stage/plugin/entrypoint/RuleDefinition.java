package org.sonar.stage.plugin.entrypoint;

import com.google.common.collect.Iterables;
import org.sonar.plugins.java.Java;
import java.util.List;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinitionAnnotationLoader;

public class RuleDefinition implements RulesDefinition {

	public static final String REPOSITORY_KEY = "Sonar-Weka-Plugin";
	public static final String REPOSITORY_NAME = "Sonar-Weka-Plugin";

	@Override
	public void define(Context context) {
		
		NewRepository repository = context.createRepository(REPOSITORY_KEY, Java.KEY).setName(REPOSITORY_NAME);
		@SuppressWarnings("rawtypes")
		List<Class> checks = RulesList.getChecks();
		new RulesDefinitionAnnotationLoader().load(repository, Iterables.toArray(checks, Class.class));
		repository.done();
	}
}