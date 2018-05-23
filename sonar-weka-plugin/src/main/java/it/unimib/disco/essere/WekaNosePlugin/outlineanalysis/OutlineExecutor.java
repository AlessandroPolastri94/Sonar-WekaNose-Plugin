package it.unimib.disco.essere.WekaNosePlugin.outlineanalysis;

import java.io.File;

import it.unimib.disco.essere.WekaNosePlugin.main.WorkSpaceHandler;
import it.unimib.disco.essere.core.InputParser;

public class OutlineExecutor {

	public OutlineExecutor(WorkSpaceHandler workspace, String path, String[] algorithmPath) throws Exception {

		InputParser outline = new InputParser();
		File toMove;

		for (String algorithm : algorithmPath) {

			String args = "-pred " + path + " " + algorithm;

			outline.start(args.replaceAll("\\\\", "/").split(" "));

			if (algorithm.equals(workspace.getAlgorithms() + "/DataClass_class.model")) {

				toMove = new File(workspace.getCorrectDatasetPath() + "/Predicted_AdaBoostM1_CorrectClassDataset.csv");
				toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/Predicted_DataClass.csv"));
			} else if (algorithm.equals(workspace.getAlgorithms() + "/GodClass_class.model")) {

				toMove = new File(workspace.getCorrectDatasetPath() + "/Predicted_NaiveBayes_CorrectClassDataset.csv");
				toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/Predicted_GodClass.csv"));
			} else if (algorithm.equals(workspace.getAlgorithms() + "/FeatureEnvy_method.model")) {

				toMove = new File(
						workspace.getCorrectDatasetPath() + "/Predicted_AdaBoostM1_CorrectMethod1Dataset.csv");
				toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/Predicted_FeatureEnvy.csv"));
			} else if (algorithm.equals(workspace.getAlgorithms() + "/LongMethod_method.model")) {

				toMove = new File(
						workspace.getCorrectDatasetPath() + "/Predicted_AdaBoostM1_CorrectMethod1Dataset.csv");
				toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/Predicted_LongMethod.csv"));
			} else if (algorithm.equals(workspace.getAlgorithms() + "/LongParameterList_method.model")) {

				toMove = new File(
						workspace.getCorrectDatasetPath() + "/Predicted_AdaBoostM1_CorrectMethod2Dataset.csv");
				toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/Predicted_LongParameterList.csv"));
			} else if (algorithm.equals(workspace.getAlgorithms() + "/SwitchStatement_method.model")) {

				toMove = new File(workspace.getCorrectDatasetPath() + "/Predicted_JRip_CorrectMethod2Dataset.csv");
				toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/Predicted_SwitchStatement.csv"));
			}
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
		}

		outline.start(args.replaceAll("\\\\", "/").split(" "));

		File toMove = new File(workspace.getDatasetPath() + "/" + predictedName);
		toMove.renameTo(new File(workspace.getOutlineAnalysisPath() + "/" + predictedName));
	}
}