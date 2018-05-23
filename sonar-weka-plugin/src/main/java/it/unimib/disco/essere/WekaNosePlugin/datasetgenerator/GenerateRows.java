package it.unimib.disco.essere.WekaNosePlugin.datasetgenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import it.unimib.disco.essere.WekaNosePlugin.main.WorkSpaceHandler;

public class GenerateRows {

	private String project = "Project";
	private WorkSpaceHandler workspace;
	private ArrayList<DatasetRow> methodRows;
	private ArrayList<DatasetRow> classRows;
	private Hashtable<Integer, Hashtable<String, String>> htmethod;
	private Hashtable<Integer, Hashtable<String, String>> htclass;
	private Hashtable<Integer, Hashtable<String, String>> htpackage;

	public GenerateRows(WorkSpaceHandler workspace) throws IOException {

		this.workspace = workspace;
		this.methodRows = new ArrayList<>();
		this.classRows = new ArrayList<>();
		htmethod = loadMetrics("QueryMethod.csv");
		htclass = loadMetrics("QueryClass.csv");
		htpackage = loadMetrics("QueryPackage.csv");
		PrintDatasetMethods();
		PrintDatasetClass();
	}

	public Hashtable<Integer, Hashtable<String, String>> loadMetrics(String type) throws IOException {

		Hashtable<Integer, Hashtable<String, String>> metrics = new Hashtable<Integer, Hashtable<String, String>>();
		String path = workspace.getJCodeOdorAnalysisPath() + "/" + type;
		List<String> file = Files.readAllLines(new File(path).toPath(), Charset.defaultCharset());

		for (String line : file) {

			String[] attr = line.replaceAll(" ", "").split(",");

			Integer key = Integer.parseInt(attr[0]);
			if (!metrics.containsKey(key)) {
				metrics.put(Integer.parseInt(attr[0]), new Hashtable<String, String>());

				String name = "";
				for (int i = 1; i < attr.length - 3; i++)
					name += attr[i] + ",";
				metrics.get(key).put("name", name.substring(0, name.length() - 1));
				metrics.get(key).put("father", attr[attr.length - 1]);
			}
			if ("QueryClass.csv".equals(type) && Arrays.asList(DatasetRow.CLASS_METRICS).contains(attr[2])) {
				metrics.get(key).put(attr[2], attr[3]);
			}
			if ("QueryMethod.csv".equals(type) && Arrays.asList(DatasetRow.METHOD_METRICS).contains(attr[attr.length - 3])) {
				metrics.get(key).put(attr[attr.length - 3], attr[attr.length - 2]);
			}
			if ("QueryPackage.csv".equals(type) && Arrays.asList(DatasetRow.PACKAGE_METRICS).contains(attr[2])) {
				metrics.get(key).put(attr[2], attr[3]);
			}

		}

		return metrics;
	}

	public void PrintDatasetMethods() throws IOException {

		for (Integer key : htmethod.keySet()) {

			int id_c = Integer.parseInt(htmethod.get(key).get("father"));
			int id_p = Integer.parseInt(htclass.get(Integer.parseInt(htmethod.get(key).get("father"))).get("father"));

			methodRows.add(new DatasetRow(key, project, htpackage.get(id_p).get("name"), htclass.get(id_c).get("name"),
					htmethod.get(key).get("name"), htmethod.get(key), htclass.get(id_c), htpackage.get(id_p)));
		}

		File file = new File(this.workspace.getDatasetPath() + "/MethodDataset.csv");
		FileWriter writer = new FileWriter(file, false);
		writer.write(DatasetRow.getHeader(true) + "\n");
		for (DatasetRow row : methodRows)
			writer.write(row.toString() + "\n");
		writer.close();
	}

	public void PrintDatasetClass() throws IOException {

		for (Integer key : htclass.keySet()) {

			int id_p = Integer.parseInt(htclass.get(key).get("father"));

			classRows.add(new DatasetRow(key, project, htpackage.get(id_p).get("name"), htclass.get(key).get("name"),
					htclass.get(key), htpackage.get(id_p)));
		}

		File file = new File(this.workspace.getDatasetPath() + "/ClassDataset.csv");
		FileWriter writer = new FileWriter(file, false);
		writer.write(DatasetRow.getHeader(false) + "\n");
		for (DatasetRow row : classRows)
			writer.write(row.toString() + "\n");
		writer.close();
	}
}