package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect 
{
	String sqlQuery = null;
	static Connection connection = null;
	Statement statement;
	
	public Connection connectDB()
	{
		try
		{
			String userName = "root";
			String password = "root";
			String dbName = "grocery";
			String url = "jdbc:mysql://localhost:3306/";
			String driver = "com.mysql.jdbc.Driver";
			
			Class.forName(driver);
			System.out.println("Drivers loaded successfully!");
			connection =  DriverManager.getConnection(url + dbName, userName, password);
			
			System.out.println("Connection established!");
			statement = connection.createStatement();
		}
		
		catch(ClassNotFoundException e)
		{
			System.out.println("Class wasn't found here.");
			e.printStackTrace();
		}
		
		catch(SQLException s)
		{
			System.out.println("The SQL error is: ");
			s.printStackTrace();
		}
		
		return connection;
	}
	
	public void disconnectDB()
	{
		try
		{
			connection.close();
			System.out.println("Connection closed.");
		}
		
		catch(SQLException e)
		{
			System.out.println("Couldn't close connection");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)  
	{
		Connect connect = new Connect();
		connect.connectDB();
		connect.disconnectDB();
	}
}