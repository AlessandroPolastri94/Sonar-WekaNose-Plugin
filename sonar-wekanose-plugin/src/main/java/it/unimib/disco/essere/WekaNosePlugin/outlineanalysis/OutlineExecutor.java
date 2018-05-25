package it.unimib.disco.essere.WekaNosePlugin.outlineanalysis;

import java.io.File;

import it.unimib.disco.essere.WekaNosePlugin.main.WorkSpaceHandler;
import it.unimib.disco.essere.core.InputParser;

public class OutlineExecutor {

	public OutlineExecutor(WorkSpaceHandler workspace, String path, String algorithmPath) throws Exception {

		InputParser outline = new InputParser();
		File toMove;

		String args = "-pred " + path + " " + algorithmPath;

		outline.start(args.replaceAll("\\\\", "/").split(" "));

		if (algorithmPath.equals(workspace.getAlgorithms() + "/DataClass_class.model")) {

			toMove = new File(workspace.getCorrectDatasetPath() + "/Predicted_AdaBoostM1_CorrectClassDataset.csv");
			toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/DataClass_class.csv"));
		} else if (algorithmPath.equals(workspace.getAlgorithms() + "/GodClass_class.model")) {

			toMove = new File(workspace.getCorrectDatasetPath() + "/Predicted_NaiveBayes_CorrectClassDataset.csv");
			toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/GodClass_class.csv"));
		} else if (algorithmPath.equals(workspace.getAlgorithms() + "/FeatureEnvy_method.model")) {

			toMove = new File(workspace.getCorrectDatasetPath() + "/Predicted_AdaBoostM1_CorrectMethod1Dataset.csv");
			toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/FeatureEnvy_method.csv"));
		} else if (algorithmPath.equals(workspace.getAlgorithms() + "/LongMethod_method.model")) {

			toMove = new File(workspace.getCorrectDatasetPath() + "/Predicted_AdaBoostM1_CorrectMethod1Dataset.csv");
			toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/LongMethod_method.csv"));
		} else if (algorithmPath.equals(workspace.getAlgorithms() + "/LongParameterList_method.model")) {

			toMove = new File(workspace.getCorrectDatasetPath() + "/Predicted_AdaBoostM1_CorrectMethod2Dataset.csv");
			toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/LongParameterList_method.csv"));
		} else if (algorithmPath.equals(workspace.getAlgorithms() + "/SwitchStatement_method.model")) {

			toMove = new File(workspace.getCorrectDatasetPath() + "/Predicted_JRip_CorrectMethod2Dataset.csv");
			toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/SwitchStatement_method.csv"));
		}
	}

	public OutlineExecutor(WorkSpaceHandler workspace, String algorithm) throws Exception {

		InputParser outline = new InputParser();

		String args = "";
		String predictedName = algorithm.substring(0, algorithm.length() - 6) + ".csv";

		if (algorithm.contains("_method.model")) {

			args += "-pred " + workspace.getDatasetPath() + "/MethodDataset.csv " + workspace.getAlgorithms() + "/"
					+ algorithm + " " + predictedName;
		} else if (algorithm.contains("_class.model")) {

			args += "-pred " + workspace.getDatasetPath() + "/ClassDataset.csv " + workspace.getAlgorithms() + "/"
					+ algorithm + " " + predictedName;
		} else
			throw new Exception("One of your algorithms missing _class or _method in his name!");

		outline.start(args.replaceAll("\\\\", "/").split(" "));

		File toMove = new File(workspace.getDatasetPath() + "/" + predictedName);
		toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/" + predictedName));
	}
}