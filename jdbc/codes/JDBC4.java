import java.sql.*;
import java.util.Scanner;

public class JDBC4 {
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        
        try 
        {
            // Load driver and establish connection
            Class.forName("org.postgresql.Driver");
            conn=DriverManager.getConnection("jdbc:postgresql://192.168.0.12/tya13","tya13","Nilesh@3304");
        
            
            if (conn != null) 
            {
                System.out.println("Connection successful");
                
                int choice;
                do 
                {
                    // Display menu
                    System.out.println("\nMENU");
                    System.out.println("1. Insert | 2. Update | 3. Delete | 4. Search | 5. View All | 0. Exit");
                    System.out.print("Choice: ");
                    choice = sc.nextInt();
                    
                    switch (choice) 
                    {
                        case 1: insertRecord(sc); break;
                        case 2: updateRecord(sc); break;
                        case 3: deleteRecord(sc); break;
                        case 4: searchRecord(sc); break;
                        case 5: viewAllRecords(); break;
                        case 0: System.out.println("Exiting..."); break;
                        default: System.out.println("Invalid choice");
                    }
                } 
                while (choice != 0);
            } 
            else
             {
                System.out.println("Connection failed");
            }
        } 
        catch (Exception e)
         {
            System.out.println("Error: " + e.getMessage());
        }
         finally
          {
            try 
            {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
              
            }
             catch (SQLException e)
              {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    private static void insertRecord(Scanner sc) throws SQLException
     {
        System.out.print("Enter roll number, name, percentage: ");
        int roll = sc.nextInt();
        sc.nextLine();
        String name = sc.nextLine();
        double percentage = sc.nextDouble();
        
     pstmt = conn.prepareStatement("INSERT INTO student (rollno, name, percentage) VALUES(?, ?, ?)");

        pstmt.setInt(1, roll);
        pstmt.setString(2, name);
        pstmt.setDouble(3, percentage);
        
        int result = pstmt.executeUpdate();
        System.out.println(result > 0 ? "Record inserted" : "Insert failed");
    }
    
    private static void updateRecord(Scanner sc) throws SQLException {
        System.out.print("Enter roll to update, new name, new percentage: ");
        int roll = sc.nextInt();
        sc.nextLine();
        String name = sc.nextLine();
        double percentage = sc.nextDouble();
        
        pstmt = conn.prepareStatement("UPDATE student SET name=?, percentage=? WHERE rollno=?");
        pstmt.setString(1, name);
        pstmt.setDouble(2, percentage);
        pstmt.setInt(3, roll);
        
        int result = pstmt.executeUpdate();
        System.out.println(result > 0 ? "Record updated" : "Update failed");
    }
    
    private static void deleteRecord(Scanner sc) throws SQLException
     {
        System.out.print("Enter roll to delete: ");
        int roll = sc.nextInt();
        
        pstmt = conn.prepareStatement("DELETE FROM student WHERE rollno=?");
        pstmt.setInt(1, roll);
        
        int result = pstmt.executeUpdate();
        System.out.println(result > 0 ? "Record deleted" : "Delete failed");
    }
    
    private static void searchRecord(Scanner sc) throws SQLException
     {
        System.out.print("Enter roll to search: ");
        int roll = sc.nextInt();
        
        pstmt = conn.prepareStatement("SELECT * FROM student WHERE rollno=?");
        pstmt.setInt(1, roll);
        rs = pstmt.executeQuery();
        
        if (rs.next())
         {
            System.out.printf("Roll: %d | Name: %s | Percentage: %.2f\n", 
                rs.getInt("rollno"), rs.getString("name"), rs.getDouble("percentage"));
        }
         else
          {
            System.out.println("Record not found");
        }
    }
    
    private static void viewAllRecords() throws SQLException 
    {
        pstmt = conn.prepareStatement("SELECT * FROM student");
        rs = pstmt.executeQuery();
        
        System.out.println("\nRoll\tName\t\tPercentage");
      
        
        while (rs.next())
         {
            System.out.printf("%d\t%-15s\t%.2f\n", 
                rs.getInt("rollno"), rs.getString("name"), rs.getDouble("percentage"));
        }
     
    }
    
} 



// CREATE DATABASE tya13;
// \c tya13

// CREATE TABLE student (
//     rollno INT PRIMARY KEY,
//     name VARCHAR(100) NOT NULL,
//     percentage DECIMAL(5,2) NOT NULL
// );
