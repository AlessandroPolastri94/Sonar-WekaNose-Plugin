package org.sonar.stage.plugin.checks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol.MethodSymbol;
import org.sonar.plugins.java.api.semantic.Symbol.TypeSymbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

@Rule(key = "CustomRule9", name = "Custom Rule 9", description = "Your Custom Rule.", tags = { "codesmells" })

public class CustomRule9 extends IssuableSubscriptionVisitor {

	private boolean type = false;
	private String predicted;
	private List<String> lines;
	private boolean instantiate = true;

	public List<Kind> nodesToVisit() {

		System.out.println("[INFO] Reading Custom Rule 9 Prediction...");
		try {

			predicted = Utils.findAlgorithms();
			if (predicted == null) {

				System.out.println(
						"[ERROR] Deactivate this rule. You need it only if you had insert a custom algorithm.");
				throw new InterruptedException();
			}
		} catch (IOException | InterruptedException e) {

			e.printStackTrace();
		}

		if (predicted.contains("_method.model")) {

			type = true;
		}

		return Utils.loadTree(type);
	}

	public void visitNode(Tree tree) {

		if (instantiate) {

			lines = new ArrayList<>();
			try {

				lines = Utils.loadPredicted(predicted);
			} catch (IOException | InterruptedException e) {

				e.printStackTrace();
			}

			instantiate = false;
		}

		if (type) {
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
								reportIssue(method.simpleName(), "Custom Rule 9 detected: " + predicted);
								break;
							}
						}
					}
				}
			}
		} else {

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
						reportIssue(_class.simpleName(), "Custom Rule 9 detected: " + predicted);
						break;
					}
				}
			}
		}
	}
}