import java.sql.*;

public class JDBC3
{
	public static void main(String[] args)throws Exception
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;

		Class.forName("org.postgresql.Driver");
		conn=DriverManager.getConnection("jdbc:postgresql://192.168.0.12/tya13","tya13","Nilesh@3304");

		if(conn!=null)
		{
			System.out.println("Connection successful...");
			stmt=conn.createStatement();

			rs=stmt.executeQuery("SELECT * FROM student");
			ResultSetMetaData rsmd=rs.getMetaData();

			int columnCount=rsmd.getColumnCount();
			System.out.println("No of columns:"+columnCount);

			for(int i=1;i<=columnCount;i++)
			{
				System.out.println("Column: "+i);
				System.out.println("Name: "+rsmd.getColumnName(i));
				System.out.println("Type: "+rsmd.getColumnTypeName(i));
				System.out.println("Display size: "+rsmd.getColumnDisplaySize(i));
			}
		conn.close();
		}
		else
		{
			System.out.println("Conncetion failed");
		}
	}
}

CREATE DATABASE tya13;
\c tya13
 CREATE TABLE users 
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
 );
