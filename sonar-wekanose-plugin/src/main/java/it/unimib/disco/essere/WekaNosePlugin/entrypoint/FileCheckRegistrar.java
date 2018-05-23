package it.unimib.disco.essere.WekaNosePlugin.entrypoint;

import java.util.List;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.api.sonarlint.SonarLintSide;

@SonarLintSide
public class FileCheckRegistrar implements CheckRegistrar {

	@Override
	public void register(RegistrarContext registrarContext) {
		
		registrarContext.registerClassesForRepository(RuleDefinition.REPOSITORY_KEY, checkClasses(), null);
	}

	public static List<Class<? extends JavaCheck>> checkClasses() {
		
		return RulesList.getJavaChecks();
	}
}