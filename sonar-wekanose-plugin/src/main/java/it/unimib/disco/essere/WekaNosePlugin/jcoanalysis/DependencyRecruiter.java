package it.unimib.disco.essere.WekaNosePlugin.jcoanalysis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import it.unimib.disco.essere.WekaNosePlugin.main.WorkSpaceHandler;

public class DependencyRecruiter {

	private String dependencyPath = "";
	private String m2 = "";

	public DependencyRecruiter(WorkSpaceHandler workspace) throws JDOMException, IOException {

		findM2(workspace.getProjectAnalyzedPath());
		File inputFile = new File(workspace.getProjectAnalyzedPath() + "/pom.xml");
		SAXBuilder saxBuilder = new SAXBuilder();
		org.jdom2.Document document = saxBuilder.build(inputFile);
		Element rootElement = document.getRootElement();
		List<Element> ElementList = rootElement.getChildren();

		for (int i = 0; i < ElementList.size(); i++) {

			Element element = ElementList.get(i);
			if (element.getName() == "dependencies") {

				List<Element> dependencies = element.getChildren();
				for (int j = 0; j < dependencies.size(); j++) {

					Element dipendency = dependencies.get(j);
					List<Element> attributes = dipendency.getChildren();
					String groupId = attributes.get(0).getValue().replaceAll("\\.", "/");
					String artifactId = attributes.get(1).getValue();
					String version = attributes.get(2).getValue();
					dependencyPath += m2 + "/" + groupId + "/" + artifactId + "/" + version + " ";
				}
			}
		}

		List<String> otherDependencies = new ArrayList<>();
		File addExternalDependencies = new File(workspace.getAddExternalDependenciesPath());
		if (addExternalDependencies.exists()) {

			otherDependencies = Files.readAllLines(addExternalDependencies.toPath(), Charset.defaultCharset());

			for (String dependency : otherDependencies) {

				File check = new File(dependency);
				if (check.isDirectory())

					dependencyPath += dependency + " ";
			}
		}
	}

	public void findM2(String path) {

		int count = 0;
		int i = 0;

		while (count < 3) {

			char temp = path.charAt(i);
			if (temp != '\\' && temp != '/') {

				m2 += temp;
			} else {

				count++;
				m2 += temp;
			}

			i++;
		}

		m2 += ".m2/repository";
	}

	public String getDependencyPath() {

		return dependencyPath;
	}
}