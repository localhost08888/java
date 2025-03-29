//Hobbyservlet.java

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/HobbyServlet")
public class HobbyServlet extends HttpServlet 
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String[] hobbies = request.getParameterValues("hobby");

        if (hobbies != null) 
        {
            Cookie[] cookies = request.getCookies();
            
            for (String hobby : hobbies) 
            {
                boolean exists = false;

                // Check if the cookie already exists
                if (cookies != null) 
                {
                    for (Cookie c : cookies)
                     {
                        if (c.getName().equals(hobby))
                         {
                            exists = true;
                            break;
                        }
                    }
                }

                // If cookie does not exist, create a new one
                if (!exists) 
                {
                    Cookie newCookie = new Cookie(hobby, "selected");
                    newCookie.setMaxAge(60 * 60 * 24);
                    response.addCookie(newCookie);
                }
            }

            out.println("<html><body>");
            out.println("<h2>Hobbies Saved:</h2>");
            for (String hobby : hobbies) 
            {
                out.println("<p>" + hobby + " has been saved as your hobby.</p>");
            }
            out.println("<a href='hobby.html'>Go Back</a>");
            out.println("</body></html>");
        }
         else
          {
            out.println("<h2>No hobbies selected!</h2>");
            out.println("<a href='hobby.html'>Go Back</a>");
        }
    }
}



hobby.html

<!DOCTYPE html>
<html>
<head>
    <title>Hobby Selection</title>
</head>
<body>
    <h2>Select Your Hobby</h2>
    <form action="HobbyServlet" method="POST">
        <input type="checkbox" name="hobby" value="Painting"> Painting<br>
        <input type="checkbox" name="hobby" value="Drawing"> Drawing<br>
        <input type="checkbox" name="hobby" value="Singing"> Singing<br>
        <input type="checkbox" name="hobby" value="Swimming"> Swimming<br>
        
        <input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </form>
</body>
</html>
