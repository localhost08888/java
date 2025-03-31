//Hobbyservlet.java

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/HobbyServlet")
public class HobbyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        HttpSession session = req.getSession();
        
        // Get selected hobby
        String hobby = req.getParameter("hobby");

        if (hobby == null) {
            pw.println("<html><body><h2>Please select a hobby.</h2>");
            pw.println("<a href='hobby.html'>Go Back</a></body></html>");
            return;
        }

        // Check if cookie already exists
        Cookie[] cookies = req.getCookies();
        boolean exists = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("hobby") && cookie.getValue().equals(hobby)) {
                    exists = true;
                    break;
                }
            }
        }

        // Set cookie only if it doesn't exist
        if (!exists) {
            Cookie hobbyCookie = new Cookie("hobby", hobby);
            hobbyCookie.setMaxAge(60 * 60 * 24); // 1 day
            res.addCookie(hobbyCookie);
            pw.println("<html><body><h2>Your hobby '" + hobby + "' has been saved!</h2>");
        } else {
            pw.println("<html><body><h2>You have already selected '" + hobby + "'!</h2>");
        }

        pw.println("<a href='hobby.html'>Go Back</a></body></html>");
        pw.close();
    }
}




hobby.html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Select Hobby</title>
</head>
<body>
    <h2>Select Your Hobby</h2>
    <form method="post" action="HobbyServlet">
        <label><input type="radio" name="hobby" value="Painting"> Painting</label><br>
        <label><input type="radio" name="hobby" value="Drawing"> Drawing</label><br>
        <label><input type="radio" name="hobby" value="Singing"> Singing</label><br>
        <label><input type="radio" name="hobby" value="Swimming"> Swimming</label><br>
        <br>
        <input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </form>
</body>
</html>


/YourProject
 ├── /src
 │    ├── HobbyServlet.java
 │
 ├── /webapp
 │    ├── hobby.html
 │
 ├── /WEB-INF
 │    ├── classes (Compiled Java files go here)

 //////// <form method="post" action="http://localhost:8080/SessionInfo">