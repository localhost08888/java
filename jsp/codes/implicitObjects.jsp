<%@ page import="java.util.*, java.text.SimpleDateFormat" %>
<%@ page import="javax.servlet.http.Cookie" %>

<html>
<head>
    <title>JSP Implicit Objects Demo</title>
</head>
<body>

    <h2>JSP Implicit Objects Demo</h2>

    <!-- Using 'out' to display current date and time -->
    <h3>Current Date and Time:</h3>
    <%
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        out.println(formatter.format(date));
    %>

    <!-- Using 'request' to get header information -->
    <h3>Request Headers:</h3>
    <%
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            out.println(headerName + " : " + request.getHeader(headerName) + "<br>");
        }
    %>

    <!-- Using 'response' to add a cookie -->
    <h3>Adding a Cookie:</h3>
    <%
        Cookie cookie = new Cookie("User", "JSP Learner");
        response.addCookie(cookie);
        out.println("Cookie 'User' with value 'JSP Learner' has been added.<br>");
    %>

    <!-- Using 'config' to get <init-param> value -->
    <h3>Servlet Init Parameter:</h3>
    <%
        String configParam = config.getInitParameter("configParam");
        out.println("Config Parameter Value: " + (configParam != null ? configParam : "Not Found") + "<br>");
    %>

    <!-- Using 'application' to get <context-param> value -->
    <h3>Application Context Parameter:</h3>
    <%
        String contextParam = application.getInitParameter("contextParam");
        out.println("Context Parameter Value: " + (contextParam != null ? contextParam : "Not Found") + "<br>");
    %>

    <!-- Using 'session' to display session ID -->
    <h3>Session ID:</h3>
    <%
        out.println("Session ID: " + session.getId() + "<br>");
    %>

    <!-- Using 'pageContext' to set and get attributes -->
    <h3>Using PageContext:</h3>
    <%
        pageContext.setAttribute("message", "Hello from PageContext!");
        out.println("PageContext Attribute Value: " + pageContext.getAttribute("message") + "<br>");
    %>

    <!-- Using 'page' to get the name of the generated servlet -->
    <h3>Generated Servlet Class:</h3>
    <%
        out.println("Generated Servlet Class Name: " + page.getClass().getName() + "<br>");
    %>

</body>
</html>
