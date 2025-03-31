  VisitServlet.java 
  import java.io.*;
  import javax.servlet.*;
  import javax.servlet.annotation.WebServlet;
  import javax.servlet.http.*;
  
  @WebServlet("/VisitCounterServlet")  // URL mapping
  public class VisitCounterServlet extends HttpServlet {
      protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
          res.setContentType("text/html;charset=UTF-8");
          PrintWriter pw = res.getWriter();
  
          // Retrieve cookies
          Cookie[] cookies = req.getCookies();
          int visitCount = 0;
          boolean isNewUser = true;
  
          if (cookies != null) {
              for (Cookie cookie : cookies) {
                  if (cookie.getName().equals("visitCount")) {
                      visitCount = Integer.parseInt(cookie.getValue());
                      isNewUser = false;
                  }
              }
          }
  
          // Increment visit count
          visitCount++;
          Cookie visitCookie = new Cookie("visitCount", String.valueOf(visitCount));
          visitCookie.setMaxAge(60 * 60 * 24 * 30); // Cookie lasts for 30 days
          res.addCookie(visitCookie);
  
          // Display message
          pw.println("<html><body>");
          if (isNewUser) {
              pw.println("<h2>Welcome! This is your first visit.</h2>");
          } else {
              pw.println("<h2>Welcome back!</h2>");
              pw.println("<p>You have visited this page " + visitCount + " times.</p>");
          }
          pw.println("</body></html>");
  
          pw.close();
      }
  }
  



index.html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Visit Counter</title>
</head>
<body>
    <h2>Track Your Visits</h2>
    <a href="VisitCounterServlet">Click Here to Track Your Visits</a>
</body>
</html>
