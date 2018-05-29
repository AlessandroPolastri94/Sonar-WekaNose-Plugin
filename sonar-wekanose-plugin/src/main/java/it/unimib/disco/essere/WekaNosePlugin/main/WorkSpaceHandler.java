package it.unimib.disco.essere.WekaNosePlugin.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class WorkSpaceHandler {

	private String projectAnalyzedPath;
	private String toolsPath;
	private String analysisPath;
	private String jCodeOdorPath;
	private String algorithmsPath;
	private String jCodeOdorAnalysisPath;
	private String datasetPath;
	private String correctDatasetPath;
	private String outlineAnalysisPath;
	private String findFilePath;
	private String addExternalDependenciesPath;

	public WorkSpaceHandler(String path, boolean findFile) throws IOException, InterruptedException {

		if (!findFile) {

			System.out.println("[INFO] Generating the workspace...");
			this.projectAnalyzedPath = path;
			this.toolsPath = findPath("sonar-wekanose-plugin-tools");
			this.jCodeOdorPath = toolsPath + "/JCodeOdor.jar";
			this.algorithmsPath = toolsPath + "/Algorithms";
			this.analysisPath = createFolder(toolsPath + "/Analysis");
			this.addExternalDependenciesPath = toolsPath + "/AddExternalDependencies.properties";
			this.jCodeOdorAnalysisPath = createFolder(analysisPath + "/JCodeOdorAnalysis");
			this.outlineAnalysisPath = createFolder(analysisPath + "/OutlineAnalysis");
			this.datasetPath = createFolder(analysisPath + "/Dataset");
			this.correctDatasetPath = createFolder(datasetPath + "/CorrectDataset");
		} else {

			findFilePath = findPath(path);
		}
	}

	private String findPath(String file) throws IOException, InterruptedException {

		Runtime r = Runtime.getRuntime();
		Process p = r.exec(new String[] { "cmd.exe", "/c", "cd C:/Users && dir /b/s " + file });
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String path = input.readLine();
		input.close();
		p.getInputStream().close();
		return path;
	}

	private String createFolder(String path) {

		File create = new File(path);
		create.mkdir();
		return path;
	}

	public void deleteFolder(String path) {

		File toDelete = new File(path);
		String[] entries = toDelete.list();
		for (String s : entries) {
			File currentFile = new File(toDelete.getPath(), s);
			currentFile.deleteOnExit();
		}
	}

	public String getProjectAnalyzedPath() {

		return projectAnalyzedPath;
	}

	public String getToolsPath() {

		return toolsPath;
	}

	public String getAnalysisPath() {

		return analysisPath;
	}

	public String getJCodeOdorPath() {

		return jCodeOdorPath;
	}

	public String getAlgorithms() {

		return algorithmsPath;
	}

	public String getAddExternalDependenciesPath() {

		return addExternalDependenciesPath;
	}

	public String getJCodeOdorAnalysisPath() {

		return jCodeOdorAnalysisPath;
	}

	public String getDatasetPath() {

		return datasetPath;
	}

	public String getCorrectDatasetPath() {

		return correctDatasetPath;
	}

	public String getOutlineAnalysisPath() {

		return outlineAnalysisPath;
	}

	public String getFindFilePath() {

		return findFilePath;
	}
}