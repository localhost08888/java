import java.sql.*;
public class JDBC2
{
	public static void main(String[] args) throws Exception
 {
		Connection conn=null;
		Class.forName("org.postgresql.Driver");
		conn=DriverManager.getConnection("jdbc:postgresql://192.168.0.12/tya13","tya13","Nilesh@3304");

		if(conn!=null)
		{
			System.out.println("Conncetion successful...");
			DatabaseMetaData dbmd=conn.getMetaData();

			System.out.println("Database Name:"+dbmd.getDatabaseProductName());
			System.out.println("Database Version:"+dbmd.getDatabaseProductVersion());
			System.out.println("Driver Name:"+dbmd.getDriverName());
			System.out.println("Driver Version:"+dbmd.getDriverVersion());

			ResultSet rs=dbmd.getTables(null,null,null,new String[]{"TABLE"});
			System.out.println("Tables in Databases");
			while(rs.next())
			{
				System.out.println(rs.getString("TABLE_NAME"));
			}
			conn.close();
		}
		else
		{
			System.out.println("Connection failed");
		}
	}
}

// CREATE DATABASE tya13;
// \c tya13
//  CREATE TABLE users 
// (
//     id SERIAL PRIMARY KEY,
//     name VARCHAR(100) NOT NULL,
//     email VARCHAR(150) UNIQUE NOT NULL,
//     password TEXT NOT NULL,
//     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
//  );
