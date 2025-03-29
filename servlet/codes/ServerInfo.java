//----------------------seta1-----------------------------
//serverInfo.java

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServerInfo extends HttpServlet
 {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException 
    {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        pw.println("<html><body><h2>Information about HTTP Request</h2>");
        pw.println("<br>Server Name: " + req.getServerName());
        pw.println("<br>Server Port: " + req.getServerPort());
        pw.println("<br>IP Address: " + req.getRemoteAddr());
        pw.println("<br>Client Browser: " + req.getHeader("User-Agent"));
        pw.println("</body></html>");
        pw.close();
    }
}

web.xml
xml
Copy
Edit
<?xml version="1.0" encoding="ISO-8859-1"?>
<web-app xmlns="http://java.sun.com/xml/ns/javaee" version="2.5">
    <servlet>
        <servlet-name>serverInfo</servlet-name>
        <servlet-class>ServerInfo</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>serverInfo</servlet-name>
        <url-pattern>/server</url-pattern>
    </servlet-mapping>
</web-app>



