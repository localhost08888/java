<%@ page import="java.util.Calendar" %>
<html>
<head>
    <title>Greeting User</title>
</head>
<body>

    <h2>Enter Your Name</h2>
    <form method="get">
        <input type="text" name="username" placeholder="Enter your name" required>
        <input type="submit" value="Submit">
    </form>

    <h2>Greeting Message:</h2>
    <%
        String username = request.getParameter("username");
        if (username != null && !username.trim().isEmpty()) {
            // Get the current hour
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            String greeting;

            // Determine greeting based on the hour
            if (hour >= 5 && hour < 12) {
                greeting = "Good Morning";
            } else if (hour >= 12 && hour < 17) {
                greeting = "Good Afternoon";
            } else {
                greeting = "Good Evening";
            }

            // Display the greeting message
            out.println("<h3>" + greeting + " " + username + "!</h3>");
        }
    %>

</body>
</html>
