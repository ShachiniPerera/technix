package com;

import java.sql.*;

public class technix {

	
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
	 }
	 catch (Exception e)
	 {
	 e.printStackTrace();
	 }
	 return con;
	 }
	
	
	public String insertStudent(String code, String FirstName, String LastName, String DOB, String UserName, String Password)
	{
		String output = "";
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for inserting.";
			}
			
			// create a prepared statement
			String query = " insert into student(`StudentID`,`code`,`FirstName`,`LastName`,`DOB`,`UserName`,`Password`) values (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, FirstName);
			preparedStmt.setString(4, LastName);
			preparedStmt.setString(5, DOB);
			preparedStmt.setString(6, UserName);
			preparedStmt.setString(7, Password);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newStudent = readstudent();
			output = "{\"status\":\"success\", \"data\": \"" +newStudent + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the Student.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	
	public String readstudent()
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'>"
					+ "<tr><th>code</th>"
					+ "<th>FirstName</th>"
					+ "<th>LastName</th>"
					+ "<th>DOB</th>"
					+ "<th>UserName</th>"
					+ "<th>Password</th>"
					+ "<th>Update</th>"
					+ "<th>Remove</th></tr>";
	
			String query = "select * from student";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next())
			{
				String StudentID = Integer.toString(rs.getInt("StudentID"));
				String code = rs.getString("code");
				String FirstName = rs.getString("FirstName");
				String LastName = rs.getString("LastName");
				String DOB = rs.getString("DOB");
				String UserName = rs.getString("UserName");
				String Password = rs.getString("Password");
				
				// Add into the html table
				output += "<tr><td><input id='hidStudentIDUpdate'name='hidStudentIDUpdate' type='hidden' value='" + StudentID + "'>" + code + "</td>";
				output += "<td>" + FirstName + "</td>";
				output += "<td>" + LastName + "</td>";
				output += "<td>" + DOB + "</td>";
				output += "<td>" + UserName + "</td>";
				output += "<td>" + Password + "</td>";
			
				// buttons
				output += "<td><input name='btnUpdate'type='button' "
						+ "value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove'type='button' "
						+ "value='Remove'class='btnRemove btn btn-danger'data-paymentid='"+ StudentID + "'>" + "</td></tr>";
			}
			
			con.close();
			
			// Complete the html table
			output += "</table>";
			
		}
		catch (Exception e)
		{
			output = "Error while reading the Student.";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	public String updateStudent(String StudentID,String code, String FirstName, String LastName, String DOB, String UserName, String Password)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for updating.";
			}
			
			// create a prepared statement
			String query = "UPDATE student SET code=?,FirstName=?,LastName=?,DOB=?,UserName=?,Password=? WHERE StudentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, FirstName);
			preparedStmt.setString(3, LastName);
			preparedStmt.setString(4, DOB);
			preparedStmt.setString(5, UserName);
			preparedStmt.setString(6, Password);
			preparedStmt.setInt(7, Integer.parseInt(StudentID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newStudent = readstudent();
			output = "{\"status\":\"success\", \"data\": \"" + newStudent + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while updating the student data.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
	
	public String deleteStudent(String StudentID)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from student where StudentID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(StudentID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newStudent = readstudent();
			output = "{\"status\":\"success\", \"data\": \"" + newStudent + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\": \"Error while deleting the Student.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
}


