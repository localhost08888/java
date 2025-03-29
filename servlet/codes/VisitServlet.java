  VisitServlet.java 

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class VisitServlet extends HttpServlet
 {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Retrieve existing cookies
        Cookie[] cookies = request.getCookies();
        int visitCount = 1;

        if (cookies != null)
         {
            for (Cookie cookie : cookies) 
            {
                if (cookie.getName().equals("visit"))
                 {
                    visitCount = Integer.parseInt(cookie.getValue()) + 1;
                }
            }
        }

        // Update visit count in cookie
        Cookie newCookie = new Cookie("visit", String.valueOf(visitCount));
        newCookie.setMaxAge(24 * 60 * 60); // 1 day expiration
        response.addCookie(newCookie);

        // Display message
        if (visitCount == 1) 
        {
            out.println("Welcome to the web page for the first time!");
        } 
        else
        {
            out.println("You have visited this page " + visitCount + " times.");
        }
    }
}

Web.xml

<?xml version="1.0" encoding="ISO-8859-1"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee" version="2.5">
    <servlet>
        <servlet-name>VisitServlet</servlet-name>
        <servlet-class>VisitServlet</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>VisitServlet</servlet-name>
        <url-pattern>/VS</url-pattern>
    </servlet-mapping>
</web-app>