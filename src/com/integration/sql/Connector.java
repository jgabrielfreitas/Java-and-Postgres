package com.integration.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	
	private Connection connection;
	private String     user;
	private String     password;
	private String     host; // ex: localhost
	private String     base; // ex: postgres
	private String     port; // ex: 5432
	
	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String machineIp) {
		this.host = machineIp;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public boolean createConnection(){
		return start();
	}

	private boolean start(){
		
		boolean connected = false;
		
		try {
			
			Class.forName("org.postgresql.Driver");
			connection = null;
			//url example "jdbc:postgresql://localhost:5432/postgres"
			String url = String.format("jdbc:postgresql://%s:%s/%s", getHost(), getPort(), getBase());
			connection = DriverManager.getConnection(url, getUser(), getPassword());
			
			connected = true;
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			connected = false;
		} catch (SQLException e) {
			
			e.printStackTrace();
			connected = false;
		} finally {
			 return connected;
		}
	}

	public Connection getConnection() {
		return connection;
	}
	
}
