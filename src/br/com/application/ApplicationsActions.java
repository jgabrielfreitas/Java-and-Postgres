package br.com.application;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import br.com.objects.Student;

import com.integration.sql.StudentDAO;

public class ApplicationsActions {
	
	public  static String 	  newline = System.getProperty("line.separator");
	private static Scanner 	  keyboard = new Scanner(System.in);
	private static StudentDAO dao;
	private static Connection myConnectionWithBase = null;

	public static void main(String[] args) {

		// create the DAO application
		dao = new StudentDAO(myConnectionWithBase);
		startApplication();
	}

	/**
	 * I usually put the user options in a single method
	 * to call him several times in different methods
	 * */
	private static void startApplication() {

		if (myConnectionWithBase == null) {
			System.out.println("Sorry, there's an error with your connection with database");
			return;
		}

		System.out.println(newline);
		System.out.println("What do you want to do?");
		System.out.println("1 - Create a new student");
		System.out.println("2 - Show all students");
		System.out.println("3 - Delete a current student");
		System.out.println("4 - Type and run a query in your base");
		System.out.println("5 - Show the number total of students");
		System.out.println("6 - Exit");

		switch (keyboard.nextLine()) {
		case "1":
			createStudent();
			break;
		case "2":
			runSelect();
			break;
		case "3":
			deleteStudent();
			break;
		case "4":
			runQuery();
			break;
		case "5":
			showCount();
			break;

		case "6":
			System.out.println("Stopping application...");
			dao.closeDataBase();
			System.out.println("Application stopped.");
			break;

		default:
			System.out.println("Sorry, try again..");
			startApplication();
			break;
		}
	}

	/**
	 * Here, you can run a script directly into the base
	 * */
	private static void runQuery() {
		System.out.println("Type the query that you want execute:");
		String queryToRun = keyboard.nextLine();
		if (dao.executeQuery(queryToRun) == true) {
			System.out.println("Your query was successful");
			startApplication();
		} else {
			System.out.println("Sorry, your query can not be executed");
			startApplication();
		}
	}

	/**
	 * This method shows just how many items exist in base
	 * */
	private static void showCount() {
		System.out.println(String.format("There is %d students in your base.", dao.getCount()));
		startApplication();
	}

	/**
	 * This method delete a student from base by id
	 * */
	private static void deleteStudent() {
		System.out.println("Type the student ID that you want delete:");
		List<Student> studentList = dao.selectAll();
		for (Student student : studentList)
			System.out.println(student);
		System.out.print("ID: ");
		String studentId = keyboard.nextLine();
		dao.deleteStudent(studentId);
		
		System.out.println("Do you want show the list again? [Y/N]");
		
		if (keyboard.nextLine().toUpperCase().equals("Y") == true)
			runSelect();
		else
			startApplication();
	}

	/**
	 * Select and show all students from base
	 * */
	private static void runSelect() {
		List<Student> studentList = dao.selectAll();
		for (Student student : studentList)
			System.out.println(student);
		startApplication();
	}
	
	/**
	 * Instance a StudentCreator and populates the object,
	 * after this, insert the base
	 * */
	private static void createStudent(){
		
		StudentCreator creator = new StudentCreator();
		creator.setConnector(getMyConnectionWithBase());
		creator.main(null);
	}

	public static Connection getMyConnectionWithBase() {
		return myConnectionWithBase;
	}

	public void setMyConnectionWithBase(Connection myConnectionWithBase) {
		this.myConnectionWithBase = myConnectionWithBase;
	}

}
