package org.sonar.stage.plugin.checks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol.TypeSymbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(key = "GodClass", name = "God Class", description = "In order to train the machine learning algorithm the following definition has been used to formalize the concept: The God Class Code Smell refers to classes that tend to centralize the intelligence of the system. A God Class tends to be complex, to have too much code, to use large amount of data from other classes and to implement several different functionalities.", tags = {
		"codesmells" })

public class GodClass extends IssuableSubscriptionVisitor {

	private List<String> lines;
	private boolean instantiate = true;

	public List<Kind> nodesToVisit() {

		System.out.println("[INFO] Reading God Class Prediction...");
		return Utils.loadTree(false);
	}

	public void visitNode(Tree tree) {

		if (instantiate) {

			lines = new ArrayList<>();
			try {

				lines = Utils.loadPredicted("Predicted_GodClass.csv");
			} catch (IOException | InterruptedException e) {

				e.printStackTrace();
			}

			instantiate = false;

		}

		ClassTree _class = (ClassTree) tree;
		TypeSymbol symbol = _class.symbol();

		List<String> splittedLine = new ArrayList<>();

		for (String line : lines) {

			splittedLine = Arrays.asList(line.split(","));
			String bool = splittedLine.get(splittedLine.size() - 1);
			if (bool.equalsIgnoreCase("true")) {

				String csvClass = Utils.extractClass(splittedLine.get(3));

				if (csvClass.equalsIgnoreCase(symbol.enclosingClass().name().toString())) {

					lines.remove(line);
					reportIssue(_class.simpleName(), "God Class smell detected!");
					break;
				}
			}
		}
	}
}