package com.market.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	public static Connection getConn() {
		try {
			
			Class.forName("org.mariadb.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mariadb://127.0.0.1:3306/marketholy", "danny", "@dada950128");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void close(ResultSet rs, Statement stmt, Connection con) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
	}

	public static void close(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
	}

	public static void close(Statement stmt) {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
	}

	public static void close(Connection con) {
		try {
			if (con != null)
				con.close();
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		}
	}
}
