//----------------------seta1-----------------------------
//InfoServlet.java

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.swing.text.View;

@WebServlet("/InfoServlet")  // Servlet mapping
public class InfoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = res.getWriter();

        // Client details
        String clientIP = req.getRemoteAddr();
        String browser = req.getHeader("User-Agent");

        // Server details
        String os = System.getProperty("os.name");
        ServletContext context = getServletContext();
        Enumeration<String> servlets = context.getServletNames();

        // Output HTML response
        pw.println("<html><body><h2>Client & Server Information</h2>");
        pw.println("<br>Client IP Address: " + clientIP);
        pw.println("<br>Client Browser: " + browser);
        pw.println("<br>Server OS: " + os);
        pw.println("<h3>Loaded Servlets:</h3><ul>");

        while (servlets.hasMoreElements()) {
            pw.println("<li>" + servlets.nextElement() + "</li>");
        }

        pw.println("</ul></body></html>");
        pw.close();
    }
}

index.html

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Server & Request Info</title>
</head>
<body>
    <h2>Server & Client Information</h2>
    <a href="http://server-ip:8080/InfoServlet">View Request & Server Info</a>


    //////// <form method="post" action="http://localhost:8080/SessionInfo">
</body>
</html>



