package com.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection connection;
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	public DBConnection(String dbURL, String user, String pwd) throws ClassNotFoundException, SQLException {

		Class.forName(JDBC_DRIVER);
		this.connection = DriverManager.getConnection(dbURL, user, pwd);
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void closeConnection() throws SQLException {
		if (this.connection != null)
			this.connection.close();
	}
}
