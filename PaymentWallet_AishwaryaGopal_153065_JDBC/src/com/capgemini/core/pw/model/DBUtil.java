package com.capgemini.core.pw.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil
{
	public static Connection getConnection() throws ClassNotFoundException,SQLException
	{
		//load driver 
		Connection con=null;
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		
		//acquire connection
		 con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
		
		return con;
	} 
}
