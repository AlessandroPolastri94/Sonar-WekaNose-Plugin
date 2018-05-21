package org.sonar.stage.plugin.outlineanalysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.sonar.stage.plugin.main.WorkSpaceHandler;

public class CorrectDatasetGenerator {

	private static final String CLASS_DATASET_HEADER = ",NOPK_project,NOCS_project,NOI_project,NOM_project,NOMNAMM_project,LOC_project,isStatic_type,NOPA_type,number_private_visibility_attributes,number_protected_visibility_attributes,number_package_visibility_attributes,num_final_attributes,num_static_attributes,num_final_static_attributes,num_not_final_not_static_attributes,num_final_not_static_attributes,num_static_not_final_attributes,number_public_visibility_methods,number_private_visibility_methods,number_protected_visibility_methods,number_package_visibility_methods,number_final_methods,number_abstract_methods,number_not_abstract_not_final_methods,number_static_methods,number_final_static_methods,number_final_not_static_methods,number_not_final_static_methods,number_not_final_not_static_methods,number_standard_design_methods,number_constructor_DefaultConstructor_methods,number_constructor_NotDefaultConstructor_methods";
	private static final String METHOD_DATASET_HEADER_1 = ",NOPK_project,NOCS_project,NOI_project,NOM_project,NOMNAMM_project,LOC_project,isStatic_type,number_private_visibility_attributes,number_protected_visibility_attributes,number_package_visibility_attributes,num_final_attributes,num_static_attributes,num_final_static_attributes,num_not_final_not_static_attributes,num_final_not_static_attributes,num_static_not_final_attributes,number_public_visibility_methods,number_private_visibility_methods,number_protected_visibility_methods,number_package_visibility_methods,number_final_methods,number_abstract_methods,number_not_abstract_not_final_methods,number_static_methods,number_final_static_methods,number_final_not_static_methods,number_not_final_static_methods,number_not_final_not_static_methods,number_standard_design_methods,number_constructor_DefaultConstructor_methods,number_constructor_NotDefaultConstructor_methods,isStatic_method";
	private static final String METHOD_DATASET_HEADER_2 = ",NOPK_project,NOCS_project,NOI_project,NOM_project,NOMNAMM_project,LOC_project";
	private static final String CLASS_DATASET_NULL = ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
	private static final String METHOD_DATASET_NULL = ",?,?,?,?,?,?";

	private List<String> datasetLines;
	private List<String> corrected;
	private String correctedPath;

	public CorrectDatasetGenerator(WorkSpaceHandler workspace, String path, String[] algorithmPath, String type)
			throws Exception {

		datasetLines = new ArrayList<>();
		corrected = new ArrayList<>();
		modify(path, type);
		printCorrectDataset(workspace, type);
		new OutlineExecutor(workspace, correctedPath, algorithmPath);
	}

	private void modify(String path, String type) throws IOException {

		datasetLines = Files.readAllLines(new File(path).toPath(), Charset.defaultCharset());
		boolean isFirstLine = true;

		if (type.equals("_class")) {

			for (String line : datasetLines) {

				if (isFirstLine) {

					corrected.add(line + CLASS_DATASET_HEADER);
					isFirstLine = false;
				} else {

					corrected.add(line + CLASS_DATASET_NULL);
				}
			}
		} else if (type.equals("_method_1")) {

			for (String line : datasetLines) {

				if (isFirstLine) {

					corrected.add(line + METHOD_DATASET_HEADER_1);
					isFirstLine = false;
				} else {

					corrected.add(line + CLASS_DATASET_NULL);
				}
			}
		} else if (type.equals("_method_2")) {

			for (String line : datasetLines) {

				if (isFirstLine) {

					corrected.add(line + METHOD_DATASET_HEADER_2);
					isFirstLine = false;
				} else {

					corrected.add(line + METHOD_DATASET_NULL);
				}
			}
		}

	}

	private void printCorrectDataset(WorkSpaceHandler workspace, String type) throws IOException {

		if (type.equals("_class")) {

			correctedPath = workspace.getCorrectDatasetPath() + "/CorrectClassDataset.csv";
			File file = new File(correctedPath);
			FileWriter writer = new FileWriter(file, false);
			for (String line : corrected)
				writer.write(line + "\n");
			writer.close();
		} else if (type.equals("_method_1")) {

			correctedPath = workspace.getCorrectDatasetPath() + "/CorrectMethod1Dataset.csv";
			File file = new File(correctedPath);
			FileWriter writer = new FileWriter(file, false);
			for (String line : corrected)
				writer.write(line + "\n");
			writer.close();
		} else if (type.equals("_method_2")) {
			
			correctedPath = workspace.getCorrectDatasetPath() + "/CorrectMethod2Dataset.csv";
			File file = new File(correctedPath);
			FileWriter writer = new FileWriter(file, false);
			for (String line : corrected)
				writer.write(line + "\n");
			writer.close();
		}
	}
}
