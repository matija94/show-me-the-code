package com.matija.dz5.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	
	private static final String url = "jdbc:postgresql://localhost:5432/ysdb";
	private static final String user = "matija";
	private static final String password = "sjm2254wow";


	private static Connection conn;
	
	public static Connection getConnection() throws SQLException{
		if (conn == null) {
			try {
				Class.forName("org.postgresql.Driver");
			}
			catch (ClassNotFoundException e) {
				// TODO: handle exception
			}
			conn = DriverManager.getConnection(url, user, password);
		}
		return conn;
	}
	
	
	
	
}
