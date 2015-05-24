package br.com.application;

import java.sql.Connection;
import java.util.Scanner;

import br.com.objects.Student;

import com.integration.sql.StudentDAO;

public class StudentCreator {

	private static Connection connection = null;
	private static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {

		Student student = new Student();
		System.out.print("Type the student name: ");
		student.setName(keyboard.nextLine());
		System.out.println("Type the student mobile phone: ");
		student.setPhone(keyboard.nextLine());

		StudentDAO dao = new StudentDAO(connection);
		dao.insert(student);

		System.out.println("Do you want create another student?? [Y/N]");
		if (keyboard.nextLine().toUpperCase().equals("Y") == true)
			main(null);
		else 
			ApplicationsActions.main(null);
	}

	public static void setConnector(Connection connection) {
		StudentCreator.connection = connection;
	}

}
