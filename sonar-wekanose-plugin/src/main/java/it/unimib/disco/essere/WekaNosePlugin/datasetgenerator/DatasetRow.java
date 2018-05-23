package it.unimib.disco.essere.WekaNosePlugin.datasetgenerator;

import java.util.Hashtable;

public class DatasetRow {

	public final static String[] METHOD_METRICS = { "NOP", "CC", "ATFD", "FDP", "CM", "MAXNESTING", "LOC", "CYCLO",
			"NMCS", "NOLV", "MaMCL", "NOAV", "LAA", "FANOUT", "CFNAMM", "ATLD", "CLNAMM", "CINT", "MeMCL", "CDISP" };

	public final static String[] CLASS_METRICS = { "NOII", "NOAM", "NOCS", "NOM", "NMO", "ATFD", "FANOUT", "NOMNAMM",
			"NOA", "NIM", "DIT", "LOC", "LOCNAMM", "CFNAMM", "TCC", "NOPA", "CBO", "RFC", "NOC", "WMC", "LCOM5", "WOC",
			"WMCNAMM", "AMW", "AMWNAMM" };

	public final static String[] PACKAGE_METRICS = { "NOCS", "NOMNAMM", "NOI", "LOC", "NOM" };

	private int m_ID;
	private String m_Project;
	private String m_Package;
	private String m_Class;
	private String m_Method;
	Hashtable<String, String> method_metrics;
	Hashtable<String, String> class_metrics;
	Hashtable<String, String> package_metrics;

	public DatasetRow(int m_ID, String m_Project, String m_Package, String m_Class, String m_Method,
			Hashtable<String, String> method_metrics, Hashtable<String, String> class_metrics,
			Hashtable<String, String> package_metrics) {

		this.m_ID = m_ID;
		this.m_Project = m_Project;
		this.m_Package = m_Package;
		this.m_Class = m_Class;
		this.m_Method = m_Method;
		this.method_metrics = method_metrics;
		this.class_metrics = class_metrics;
		this.package_metrics = package_metrics;
	}

	public DatasetRow(int m_ID, String m_Project, String m_Package, String m_Class,
			Hashtable<String, String> class_metrics, Hashtable<String, String> package_metrics) {

		this.m_ID = m_ID;
		this.m_Project = m_Project;
		this.m_Package = m_Package;
		this.m_Class = m_Class;
		this.class_metrics = class_metrics;
		this.package_metrics = package_metrics;
	}

	public String toString() {
		String string = m_ID + "," + m_Project + "," + m_Package + "," + m_Class + ",";

		if (this.method_metrics != null) {
			string += "\"" + m_Method + "\",";
			for (String metric : METHOD_METRICS) {
				String value = this.method_metrics.get(metric);
				if (value != null)
					string += this.method_metrics.get(metric) + ",";
				else
					string += "?,";
			}
		}

		for (String metric : CLASS_METRICS) {
			String value = this.class_metrics.get(metric);
			if (value != null)
				string += this.class_metrics.get(metric) + ",";
			else
				string += "?,";
		}

		for (String metric : PACKAGE_METRICS) {
			try {
				string += this.package_metrics.get(metric) + ",";
			} catch (NullPointerException e) {
				string += "?,";
			}
		}

		return string.substring(0, string.length()-1);
	}

	public static String getHeader(boolean isMethodLevel) {

		String header = "ID,Project,Package,Class,";
		if (isMethodLevel) {
			header += "Method,";
			for (String metric : METHOD_METRICS) {
				header += metric + "_method,";
			}
		}
		for (String metric : CLASS_METRICS) {
			header += metric + "_class,";
		}
		for (String metric : PACKAGE_METRICS) {
			header += metric + "_package,";
		}
		
		return header.substring(0, header.length()-1);
	}
}
