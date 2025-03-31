//searchstudentservlet.java

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SearchStudentServlet")
public class SearchStudentServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASS = "your_password";

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = res.getWriter();
        String rollNo = req.getParameter("rollNo");

        try {
            // Load PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = con.prepareStatement("SELECT * FROM student WHERE roll_no = ?");
            ps.setString(1, rollNo);
            ResultSet rs = ps.executeQuery();

            pw.println("<html><body><h2>Student Details</h2>");
            if (rs.next()) {
                pw.println("<p><strong>Roll No:</strong> " + rs.getString("roll_no") + "</p>");
                pw.println("<p><strong>Name:</strong> " + rs.getString("name") + "</p>");
                pw.println("<p><strong>Course:</strong> " + rs.getString("course") + "</p>");
            } else {
                pw.println("<p style='color:red;'>No student found with Roll No: " + rollNo + "</p>");
            }
            pw.println("</body></html>");

            // Close resources
            rs.close();
            ps.close();
            con.close();
        } catch (Exception e) {
            pw.println("<p style='color:red;'>Database Connection Error: " + e.getMessage() + "</p>");
        }
        pw.close();
    }
}




//index.html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Search</title>
</head>
<body>
    <h2>Search Student Details</h2>

    //////// <form method="post" action="http://localhost:8080/SessionInfo">
    <form method="post" action="SearchStudentServlet">
        Enter Roll Number: <input type="text" name="rollNo" required>
        <br><br>
        <input type="submit" value="Search">
    </form>
</body>
</html>



//database
CREATE TABLE student (
    roll_no VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100),
    course VARCHAR(50)
);

INSERT INTO student VALUES ('101', 'Alice Johnson', 'Computer Science');
INSERT INTO student VALUES ('102', 'Bob Smith', 'Mathematics');
