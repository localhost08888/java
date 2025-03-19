<%@ page import="java.io.*" %>
<html>
<head>
    <title>File List by Extension</title>
</head>
<body>
    <h2>Enter File Extension</h2>
    <form method="get">
        <input type="text" name="ext" placeholder="e.g., txt, jsp, java">
        <input type="submit" value="Search">
    </form>

    <h2>Files Found:</h2>
    <%
        String ext = request.getParameter("ext");
        if (ext != null && !ext.trim().isEmpty()) {
            File directory = new File(application.getRealPath("/"));
            File[] files = directory.listFiles((dir, name) -> name.endsWith("." + ext));

            if (files != null && files.length > 0) {
                for (File file : files) {
                    out.println("<a href='" + file.getName() + "'>" + file.getName() + "</a><br>");
                }
            } else {
                out.println("No files found with extension: " + ext);
            }
        }
    %>
</body>
</html>
