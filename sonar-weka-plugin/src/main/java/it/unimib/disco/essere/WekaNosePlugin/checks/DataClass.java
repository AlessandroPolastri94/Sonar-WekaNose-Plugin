package it.unimib.disco.essere.WekaNosePlugin.checks;

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

@Rule(key = "DataClass", name = "Data Class", description = "In order to train the machine learning algorithm the following definition has been used to formalize the concept: The Data Class Code Smell refers to classes that store data without using complex functionality, and having other classes that strongly rely on them. A Data Class reveals many attributes, it is not complex, and it provides data field through accessor methods.", tags = {
		"codesmells" })

public class DataClass extends IssuableSubscriptionVisitor {

	private List<String> lines;
	private boolean instantiate = true;

	public List<Kind> nodesToVisit() {

		System.out.println("[INFO] Reading Data Class Prediction...");
		return Utils.loadTree(false);
	}

	public void visitNode(Tree tree) {

		if (instantiate) {

			lines = new ArrayList<>();
			try {

				lines = Utils.loadPredicted("Predicted_DataClass.csv");
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
					reportIssue(_class.simpleName(), "Data Class smell detected!");
					break;
				}
			}
		}
	}
}