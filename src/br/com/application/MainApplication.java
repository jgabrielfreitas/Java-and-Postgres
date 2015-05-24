package br.com.application;

import java.util.Scanner;

import com.integration.sql.Connector;

public class MainApplication {
	
	private static Scanner keyboard = new Scanner(System.in);
	private static Connector connector = null;
	
	/**
	 * The application start here.
	 * It is very important to create the connection to the database here.
	 * So, login and you can use application
	 * */
	public static void main(String[] args) {
		if (createConnection() == true) {
			System.out.println("Now you're logged!");
			ApplicationsActions actions = new ApplicationsActions();
			actions.setMyConnectionWithBase(connector.getConnection());
			actions.main(null);
		} else {
			System.out.println("Sorry, an error occured, check log debug for more informations.");
		}
			
	}
	
	// login method
	private static boolean createConnection() {
		
		System.out.println("You're not connected in database. Please, login.");
		connector = new Connector();
		
		System.out.println("Your user:");
		connector.setUser(keyboard.nextLine());
		
		System.out.println("Your password:");
		connector.setPassword(keyboard.nextLine());
		
		System.out.println("Your host: "); // localhost
		connector.setHost(keyboard.nextLine());
		
		System.out.println("Your port:");
		connector.setPort(keyboard.nextLine());
		
		System.out.println("Your base:");
		connector.setBase(keyboard.nextLine());
		
		return connector.createConnection();
	}

}
