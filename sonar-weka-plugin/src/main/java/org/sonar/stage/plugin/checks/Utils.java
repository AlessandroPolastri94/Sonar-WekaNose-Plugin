package org.sonar.stage.plugin.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import com.google.common.collect.ImmutableList;

public class Utils {

	private static File fileAnalyzed;
	private static File algorithms;
	
	public static List<Kind> loadTree(boolean isMethod) {

		List<Kind> merge = new ArrayList<>();
		if (isMethod) {

			List<Kind> temp = ImmutableList.of(Kind.METHOD);

			for (Kind method : temp) {

				merge.add(method);
			}

			temp = ImmutableList.of(Kind.CONSTRUCTOR);

			for (Kind contructor : temp) {

				merge.add(contructor);
			}

			return Collections.unmodifiableList(merge);
		} else {

			return ImmutableList.of(Tree.Kind.CLASS);
		}
	}

	public static List<String> loadPredicted(String csvName) throws IOException, InterruptedException {

		WorkSpaceHandler workspace = new WorkSpaceHandler(csvName, true);
		List<String> temp = new ArrayList<>();
		fileAnalyzed = new File(workspace.getFindFilePath());
		temp = Files.readAllLines(fileAnalyzed.toPath(), Charset.defaultCharset());
		deleteSource();
		return temp;
	}

	public static String findAlgorithms() throws IOException, InterruptedException {

		WorkSpaceHandler workspace = new WorkSpaceHandler("Algorithms", true);
		algorithms = new File(workspace.getFindFilePath());

		String[] entries = algorithms.list();
		for (String algorithm : entries) {

			if (!algorithm.equalsIgnoreCase("DataClass_class.model")
					&& !algorithm.equalsIgnoreCase("GodClass_class.model")
					&& !algorithm.equalsIgnoreCase("FeatureEnvy_method.model")
					&& !algorithm.equalsIgnoreCase("LongMethod_method.model")
					&& !algorithm.equalsIgnoreCase("LongParameterList_method.model")
					&& !algorithm.equalsIgnoreCase("SwitchStatement_method.model")) {

				return algorithm.substring(0, algorithm.length() - 6) + ".csv";
			}
		}
		return null;
	}

	public static String extractMethodName(String name) {

		String correctName = "";

		boolean flag = true;
		for (int i = 0; flag; i++) {

			char temp = name.charAt(i);
			if (temp == '(') {

				flag = false;
			} else if (temp != '\'') {

				correctName += temp;
			}
		}
		return correctName;
	}

	public static String extractClass(String name) {

		String methodClass = "";

		if (!name.contains(".")) {

			methodClass = name;
		} else {

			methodClass = name.substring(name.lastIndexOf(".") + 1);
		}

		return methodClass;
	}

	public static List<String> extractMethodParameters(String line) {

		List<String> splittedLine = new ArrayList<>();
		List<String> methodParameters = new ArrayList<>();

		splittedLine = Arrays.asList(line.split("\\("));
		splittedLine = Arrays.asList(splittedLine.get(1).split("\\)"));
		splittedLine = Arrays.asList(splittedLine.get(0).split(","));

		for (String parameter : splittedLine) {

			methodParameters.add(parameter.substring(parameter.lastIndexOf(".") + 1));
		}

		return methodParameters;
	}

	private static void deleteSource() {

		System.out.println("[INFO] Deleting useless predict file...");
		fileAnalyzed.deleteOnExit();
	}
}