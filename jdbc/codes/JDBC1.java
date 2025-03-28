import java.sql.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class JDBC1
 {
    public static void main(String[] args) throws Exception 
		{
        Connection conn = null;
        PreparedStatement psInsert = null;
        Statement stmt = null;
        ResultSet rs = null;

        Scanner scanner = new Scanner(System.in);

        Class.forName("org.postgresql.Driver");

        conn = DriverManager.getConnection("jdbc:postgresql://192.168.0.12/tya13", "examx", "exampasss");
        psInsert = conn.prepareStatement("INSERT INTO student(rollno, name, percentage) VALUES (?, ?, ?)");

        if (conn != null)
 {
            System.out.println("Connection successful...");

            System.out.print("Enter roll number: ");
            int rollNo = scanner.nextInt();
            System.out.print("Enter name: ");
            scanner.nextLine(); // Consume the leftover newline
            String sname = scanner.nextLine();
            System.out.print("Enter percentage: ");
            double percentage = scanner.nextDouble();

            psInsert.setInt(1, rollNo);
            psInsert.setString(2, sname);
            psInsert.setDouble(3, percentage);

            psInsert.executeUpdate();
            System.out.println("Record inserted successfully!");

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM student");

            JFrame frame = new JFrame("Student Details");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Roll Number");
            model.addColumn("Name");
            model.addColumn("Percentage");

            while (rs.next()) 
						{
                model.addRow(new Object[]
								{
                        rs.getInt("rollno"),
                        rs.getString("name"),
                        rs.getDouble("percentage") 
                });
            }
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane);
            frame.setVisible(true);
        }

        
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (psInsert != null) psInsert.close();
        if (conn != null) conn.close();
        scanner.close();
    }
}

// CREATE TABLE student (
//     rollno INT PRIMARY KEY,
//     name VARCHAR(100) NOT NULL,
//     percentage DECIMAL(5,2) NOT NULL
// );
