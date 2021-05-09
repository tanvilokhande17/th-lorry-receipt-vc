package com.amazonaws.lambda.signin.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConfig {

	private static String url = ApplicationProperties.getProperty("database.mysql.url");
	private static String username = ApplicationProperties.getProperty("database.mysql.username");
	private static String password = ApplicationProperties.getProperty("database.mysql.password");

	private static Connection jdbcConnection;

	public static Connection getConnection() throws SQLException {

		if (jdbcConnection == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			jdbcConnection = DriverManager.getConnection(url, username, password);
		}
		return jdbcConnection;
	}
}
