package org.sonar.stage.plugin.entrypoint;

import org.sonar.api.Plugin;
import org.sonar.stage.plugin.main.PlugInSensor;

public class Main implements Plugin {

	@Override
	public void define(Context context) {
		
		context.addExtension(RuleDefinition.class);
		context.addExtension(FileCheckRegistrar.class);
		context.addExtension(PlugInSensor.class);
	}
}