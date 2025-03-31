CREATE DATABASE UserDB;
\c UserDB;

CREATE TABLE Login (
    login_name VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);

 
INSERT INTO Login (login_name, password) VALUES ('admin', 'admin123');

 login.html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>
    <h2>Login</h2>
    <form method="post" action="LoginServlet">
        <label>Username: <input type="text" name="login_name" required></label><br><br>
        <label>Password: <input type="password" name="password" required></label><br><br>
        <input type="submit" value="Login">
    </form>
</body>
</html>



loginservlet.javaimport java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        
        // Retrieve user input
        String loginName = req.getParameter("login_name");
        String password = req.getParameter("password");

        // PostgreSQL connection details
        String jdbcURL = "jdbc:postgresql://localhost:5432/UserDB";
        String dbUser = "postgres";
        String dbPass = "your_password"; // Change this to your PostgreSQL password

        boolean isValidUser = false;

        try {
            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(jdbcURL, dbUser, dbPass);

            // Validate user
            String query = "SELECT * FROM Login WHERE login_name=? AND password=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, loginName);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                isValidUser = true;
            }

            con.close();
        } catch (Exception e) {
            pw.println("<h3>Error: " + e.getMessage() + "</h3>");
            return;
        }

        if (isValidUser) {
            // Check for existing cookie tracking login attempts
            Cookie[] cookies = req.getCookies();
            int loginCount = 1;
            boolean found = false;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("login_count")) {
                        loginCount = Integer.parseInt(cookie.getValue()) + 1;
                        found = true;
                        break;
                    }
                }
            }

            // Create/update login count cookie
            Cookie loginCookie = new Cookie("login_count", String.valueOf(loginCount));
            loginCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
            res.addCookie(loginCookie);

            // Success response
            pw.println("<html><body>");
            pw.println("<h2>Login Successful!</h2>");
            pw.println("<p>Welcome, " + loginName + ".</p>");
            pw.println("<p>You have successfully logged in " + loginCount + " times.</p>");
            pw.println("</body></html>");
        } else {
            pw.println("<html><body><h2>Login Failed!</h2><p>Invalid username or password.</p>");
            pw.println("<a href='login.html'>Try Again</a>");
            pw.println("</body></html>");
        }
    }
}
