package com.integration.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;

import br.com.objects.Student;

public class StudentDAO {
	
	private final String DATABASE_NAME = "student_db";
	private Connection connection = null;
	
	public StudentDAO(Connection connection) {
		this.connection = connection;
		createStudentTable();
	}
	
	// try create student_db table if not exists
	private void createStudentTable(){
		
		String checkIfStudentTableExistsQuery = "CREATE TABLE IF NOT EXISTS " + DATABASE_NAME + " (ID SERIAL PRIMARY KEY, NAME TEXT, PHONE_NUMBER TEXT);";
		
		try {
			Statement statement = connection.createStatement();
			statement.execute(checkIfStudentTableExistsQuery);
			statement.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// delete student from base by id
	public void deleteStudent(String studentID){
		
		Statement statement = null;
		String deleteQuery = String.format("DELETE FROM %s WHERE ID = %s" , DATABASE_NAME, studentID);
		
		try {
			
			statement = connection.createStatement();
			
			statement.execute(deleteQuery);
			System.out.println("Student deleted.");
			statement.close();
			
		} catch (Exception e) {
			System.out.println("Student was not deleted.");
			e.printStackTrace();
		}
	}
	
	// get all students from base and return a list with all
	public List<Student> selectAll() {

		List<Student> listFromBase = new ArrayList<>();
		
		Statement statement = null;
		String selectQuery = String.format("SELECT * FROM %s", DATABASE_NAME);
		try {
			
			statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(selectQuery);
			
			while (resultSet.next()) {
				Student studentFromBase = new Student();
				studentFromBase.setBaseId(resultSet.getInt("ID"));
				studentFromBase.setName(resultSet.getString("NAME"));
				studentFromBase.setPhone(resultSet.getString("PHONE_NUMBER"));
				listFromBase.add(studentFromBase);
			}
			
		} catch ( Exception e) {
			e.printStackTrace();
		}
		
		return listFromBase;
	}
	
	// insert a student into base
 	public void insert(Student student){
		
		String query = "INSERT INTO " + DATABASE_NAME +
			   	   	   " (NAME, PHONE_NUMBER) VALUES ('" + student.getName() +
			   	   	   "' , '" + student.getPhone() + "' );";
		
		Statement statement = null;
		
		try {
			
			if (connection == null)
				throw new Exception();
			
			connection.setAutoCommit(false);
			
			statement = connection.createStatement();
			statement.execute(query);
			statement.close();
			
			connection.commit();
			System.out.println("Student added");
		} catch (PSQLException psql) {
			// base is closed, so reconnect and try again
			psql.printStackTrace();
			Connector connector = new Connector();
			if (connector.createConnection())
				insert(student);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
 	// closes the session with the base
	public void closeDataBase(){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// get the total of items in base
	public int getCount() {

		int result = 0;
		Statement statement = null;
		String selectQuery = String.format("SELECT * FROM %s", DATABASE_NAME);

		try {

			statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectQuery);

			while (resultSet.next())
				result++;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	// run your query
	public boolean executeQuery(String queryToRun) {
		
		boolean success = false;
		Statement statement = null;
		
		try {
			
			connection.setAutoCommit(false);
			
			statement = connection.createStatement();
			statement.execute(queryToRun);
			statement.close();
			connection.commit();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}
	
}
