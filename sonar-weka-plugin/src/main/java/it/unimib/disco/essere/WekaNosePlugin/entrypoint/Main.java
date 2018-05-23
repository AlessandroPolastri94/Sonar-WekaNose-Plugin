package it.unimib.disco.essere.WekaNosePlugin.entrypoint;

import org.sonar.api.Plugin;

import it.unimib.disco.essere.WekaNosePlugin.main.PlugInSensor;

public class Main implements Plugin {

	@Override
	public void define(Context context) {
		
		context.addExtension(RuleDefinition.class);
		context.addExtension(FileCheckRegistrar.class);
		context.addExtension(PlugInSensor.class);
	}
}