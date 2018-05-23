package it.unimib.disco.essere.WekaNosePlugin.checks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol.MethodSymbol;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(key = "LongMethod", name = "Long Method", description = "In order to train the machine learning algorithm the following definition has been used to formalize the concept: The Long Method Code Smell refers to methods that tend to centralize the functionality of a class. A Long Method tends to have too much code, to be complex, to be difficult to understand and to use large amount of data from other classes.", tags = {
		"codesmells" })

public class LongMethod extends IssuableSubscriptionVisitor {

	private List<String> lines;
	private boolean instantiate = true;

	public List<Kind> nodesToVisit() {

		System.out.println("[INFO] Reading Long Method Prediction...");
		return Utils.loadTree(true);
	}

	public void visitNode(Tree tree) {

		if (instantiate) {

			lines = new ArrayList<>();
			try {

				lines = Utils.loadPredicted("Predicted_LongMethod.csv");
			} catch (IOException | InterruptedException e) {

				e.printStackTrace();
			}

			instantiate = false;

		}

		MethodTree method = (MethodTree) tree;
		MethodSymbol symbol = method.symbol();

		List<String> splittedLine = new ArrayList<>();

		for (String line : lines) {

			splittedLine = Arrays.asList(line.split(","));
			String bool = splittedLine.get(splittedLine.size() - 1);
			if (bool.equalsIgnoreCase("true")) {

				String methodClass = Utils.extractClass(splittedLine.get(3));

				if (methodClass.equalsIgnoreCase(symbol.enclosingClass().name().toString())) {

					String methodName = Utils.extractMethodName(splittedLine.get(4));

					if (methodName.equalsIgnoreCase(method.simpleName().toString())) {

						List<String> methodParameters = new ArrayList<>();
						methodParameters = Utils.extractMethodParameters(line);

						int count = 0;
						int countDeleted = 0;
						for (VariableTree sonarParameter : method.parameters()) {

							for (String csvParameter : methodParameters) {
								if (sonarParameter.symbol().type().toString().equalsIgnoreCase(csvParameter)) {

									methodParameters.remove(csvParameter);
									countDeleted++;
									break;
								}
							}
							count++;
						}

						if (count == countDeleted) {

							lines.remove(line);
							reportIssue(method.simpleName(), "Long Method smell detected!");
							break;
						}
					}
				}
			}
		}
	}
}