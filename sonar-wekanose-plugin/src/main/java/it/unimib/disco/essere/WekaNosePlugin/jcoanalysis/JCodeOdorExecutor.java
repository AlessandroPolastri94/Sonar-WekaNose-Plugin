package it.unimib.disco.essere.WekaNosePlugin.jcoanalysis;

import java.util.ArrayList;
import java.util.List;

public class JCodeOdorExecutor {

	public JCodeOdorExecutor(String jarFilePath, List<String> args) throws Exception {

		final List<String> actualArgs = new ArrayList<String>();
		actualArgs.add(0, "java");
		actualArgs.add(1, "-jar");
		actualArgs.add(2, jarFilePath);
		actualArgs.addAll(args);
		Runtime r = Runtime.getRuntime();
		Process p = r.exec(actualArgs.toArray(new String[0]));
		p.getInputStream().close();
	}
}
