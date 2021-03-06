package it.unimib.disco.essere.WekaNosePlugin.checks;

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

@Rule(key = "Rule07", name = "Rule 07", description = "Rule 07", tags = { "codesmells" })

public class Rule07 extends IssuableSubscriptionVisitor {

	private boolean type = false;
	private String predicted;
	private List<String> lines;
	private boolean instantiate = true;
	private String smellName;

	public List<Kind> nodesToVisit() {

		try {

			predicted = Utils.findAlgorithms();
			if (predicted == null) {

				throw new Exception(
						"[ERROR] This rule is not needed, deactivate it. Remember to activate as many rules as the inserted algorithms!");
			} else {

				smellName = predicted.substring(0, predicted.indexOf("_"));
				System.out.println("[INFO] Reading " + smellName + " analysis...");
			}
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}

		if (predicted.contains("_method.csv")) {

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
								reportIssue(method.simpleName(), smellName);
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
						reportIssue(_class.simpleName(), smellName);
						break;
					}
				}
			}
		}
	}
}