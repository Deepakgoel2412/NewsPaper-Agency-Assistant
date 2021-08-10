package database_connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnectivity {

	public static Connection getConnection()
	{
		Connection con=null;
		try {
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project_npaa?characterEncoding=utf8", "root", "root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;

	}
	
	public static void main(String[] args) {
		
		Connection conRef=getConnection();
		if(conRef==null)
			System.out.println("error");
		else
			System.out.println("Connected...");
	}

}
