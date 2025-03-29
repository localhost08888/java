//searchstudentservlet.java

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/SearchStudentServlet")  
public class SearchStudentServlet extends HttpServlet 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String rollno = request.getParameter("rollno");  // Get roll number from HTML form

        
        String url = "jdbc:postgresql://192.168.0.12:5432/examx";  // PostgreSQL port is 5432
        String user = "examx";
        String password = "examxpass";

        try
         {
       
            Class.forName("org.postgresql.Driver");
 
            Connection conn = DriverManager.getConnection(url, user, password);
 
            String query = "SELECT * FROM student WHERE rollno = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, rollno);
            ResultSet rs = ps.executeQuery();

            out.println("<html><body>");
            if (rs.next())
             {
                
                String name = rs.getString("name");
                double percentage = rs.getDouble("percentage");

               
                out.println("<h2>Student Details</h2>");
                out.println("<p><b>Roll Number:</b> " + rollno + "</p>");
                out.println("<p><b>Name:</b> " + name + "</p>");
                out.println("<p><b>Percentage:</b> " + percentage + "%</p>");
            }
             else 
             {
                out.println("<h2 style='color:red;'>Student Not Found!</h2>");
            }
            out.println("</body></html>");

             
            rs.close();
            ps.close();
            conn.close();
        } 
        catch (Exception e)
         {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}




//index.html

<!DOCTYPE html>
<html>
<head>
    <title>Search Student</title>
</head>
<body>
    <h2>Search Student Details</h2>
    <form action="SearchStudentServlet" method="GET">
        <label for="rollno">Enter Roll Number:</label>
        <input type="text" id="rollno" name="rollno" required>
        <input type="submit" value="Search">
    </form>
</body>
</html>



//database
CREATE TABLE student 
(
    rollno VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100),
    percentage DOUBLE PRECISION
);

INSERT INTO student VALUES 
('101', 'Alice Brown', 85.5),
('102', 'Bob Smith', 78.2);
