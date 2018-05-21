package org.sonar.stage.plugin.main;

import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.stage.plugin.datasetgenerator.GenerateRows;
import org.sonar.stage.plugin.jcoanalysis.DependencyRecruiter;
import org.sonar.stage.plugin.jcoanalysis.JCodeOdorDBHandler;
import org.sonar.stage.plugin.jcoanalysis.JCodeOdorExecutor;
import org.sonar.stage.plugin.outlineanalysis.CorrectDatasetGenerator;
import org.sonar.stage.plugin.outlineanalysis.OutlineExecutor;

import java.io.File;
import java.util.Arrays;
import org.sonar.api.batch.fs.FileSystem;

public class PlugInSensor implements Sensor {

	private FileSystem fs;
	private WorkSpaceHandler workspace;

	@Override
	public void describe(SensorDescriptor descriptor) {

	}

	@Override
	public void execute(org.sonar.api.batch.sensor.SensorContext context) {

	}

	public PlugInSensor(FileSystem fs) throws Exception {

		this.fs = fs;
		this.workspace = new WorkSpaceHandler(getBaseDir(), false);
		System.out.println("[INFO] Obtaining project dependency...");
		DependencyRecruiter dr = new DependencyRecruiter(workspace);
		String SQLiteFilePath = workspace.getJCodeOdorAnalysisPath() + "/JCOAnalysis.SQLite";
		String arg = "-source " + getBaseDir() + " -lib " + dr.getDependencyPath() + " -output " + SQLiteFilePath;
		System.out.println("[INFO] Generating JCodeOdorAnalysis...");
		new JCodeOdorExecutor(workspace.getJCodeOdorPath(), Arrays.asList(arg.split(" ")));
		while (!new File(SQLiteFilePath).exists()) {

			Thread.sleep(100);
		}
		System.out.println("[INFO] Selecting the correct metrics...");
		new JCodeOdorDBHandler(SQLiteFilePath);
		System.out.println("[INFO] Generating class and method datasets...");
		new GenerateRows(workspace);
		System.out.println("[INFO] Analysing datasets with basic algoritms...");
		new CorrectDatasetGenerator(workspace, workspace.getDatasetPath() + "/ClassDataset.csv",
				new String[] { workspace.getAlgorithms() + "/DataClass_class.model",
						workspace.getAlgorithms() + "/GodClass_class.model" },
				"_class");
		new CorrectDatasetGenerator(workspace, workspace.getDatasetPath() + "/MethodDataset.csv",
				new String[] { workspace.getAlgorithms() + "/FeatureEnvy_method.model",
						workspace.getAlgorithms() + "/LongMethod_method.model" },
				"_method_1");
		new CorrectDatasetGenerator(workspace, workspace.getDatasetPath() + "/MethodDataset.csv",
				new String[] { workspace.getAlgorithms() + "/LongParameterList_method.model",
						workspace.getAlgorithms() + "/SwitchStatement_method.model" },
				"_method_2");

		System.out.println("[INFO] Analyzing dataset with yours algorithms...");
		File index = new File(workspace.getAlgorithms());
		String[] entries = index.list();
		for (String algorithm : entries) {

			if (!algorithm.equalsIgnoreCase("DataClass_class.model")
					&& !algorithm.equalsIgnoreCase("GodClass_class.model")
					&& !algorithm.equalsIgnoreCase("FeatureEnvy_method.model")
					&& !algorithm.equalsIgnoreCase("LongMethod_method.model")
					&& !algorithm.equalsIgnoreCase("LongParameterList_method.model")
					&& !algorithm.equalsIgnoreCase("SwitchStatement_method.model")) {

				new OutlineExecutor(workspace, algorithm);
			}
		}

		System.out.println("[INFO] Completed!");
		System.out.println("[INFO] Deleting useless files...");
		workspace.deleteFolder(workspace.getJCodeOdorAnalysisPath());
		workspace.deleteFolder(workspace.getCorrectDatasetPath());
		workspace.deleteFolder(workspace.getDatasetPath());
		System.out.println("[INFO] Done!");
	}

	public String getBaseDir() {

		return fs.baseDir().getAbsolutePath();
	}

	public WorkSpaceHandler getWorkspace() {

		return workspace;
	}
}