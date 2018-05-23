package it.unimib.disco.essere.WekaNosePlugin.jcoanalysis;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.*;

public class JCodeOdorDBHandler {

	private String dbPath;

	public JCodeOdorDBHandler(String dbPath)
			throws ClassNotFoundException, SQLException, FileNotFoundException, UnsupportedEncodingException {

		Connection c = null;
		Statement stmt = null;

		this.dbPath = dbPath;

		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		c.setAutoCommit(false);
		stmt = c.createStatement();
		queryMethod(stmt);
		queryClass(stmt);
		queryPackage(stmt);
		stmt.close();
		c.close();
	}

	public void queryMethod(Statement stmt) throws SQLException, FileNotFoundException, UnsupportedEncodingException {

		ResultSet rs = stmt.executeQuery("Select Measurables.id,  Measurables.name, Measures.key, Measures.value, "
				+ "Measurables.parent " + "From Measurables, Measures "
				+ "Where Measures.measurable = Measurables.id AND Measures.type = \"metric\" "
				+ "AND Measurables.type = \"method\" " + "Order by Measures.id");
		this.printResultQuery(rs, "QueryMethod");
		rs.close();
	}

	public void queryClass(Statement stmt) throws SQLException, FileNotFoundException, UnsupportedEncodingException {

		ResultSet rs = stmt.executeQuery("Select Measurables.id,  Measurables.name, Measures.key, Measures.value, "
				+ "Measurables.parent " + "From Measurables, Measures "
				+ "Where Measures.measurable = Measurables.id AND Measures.type = \"metric\" "
				+ "AND Measurables.type <> \"package\" AND Measurables.type <> \"method\" "
				+ "AND Measurables.type <> \"project\" " + "Order by Measures.id");
		this.printResultQuery(rs, "QueryClass");
		rs.close();
	}

	public void queryPackage(Statement stmt) throws SQLException, FileNotFoundException, UnsupportedEncodingException {

		ResultSet rs = stmt.executeQuery("Select Measurables.id,  Measurables.name, Measures.key, Measures.value, "
				+ "Measurables.parent " + "From Measurables, Measures "
				+ "Where Measures.measurable = Measurables.id AND Measures.type = \"metric\" "
				+ "AND Measurables.type = \"package\" " + "Order by Measures.id ");
		this.printResultQuery(rs, "QueryPackage");
		rs.close();
	}

	public void printResultQuery(ResultSet rs, String name)
			throws SQLException, FileNotFoundException, UnsupportedEncodingException {

		PrintWriter writer = new PrintWriter(
				this.dbPath.substring(0, this.dbPath.lastIndexOf('/')) + "/" + name + ".csv", "UTF-8");

		while (rs.next()) {

			String row = "";
			for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {

				row += rs.getString(i) + ", ";
			}
			writer.println(row);

		}
		writer.close();
	}
}
