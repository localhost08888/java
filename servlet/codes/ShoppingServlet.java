page1.html

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Page 1</title>
</head>
<body>
    <h2>Shopping Page 1</h2>
    <form method="post" action="ShoppingServlet">
        <label>Item A ($10): <input type="checkbox" name="item" value="10"></label><br>
        <label>Item B ($20): <input type="checkbox" name="item" value="20"></label><br>
        <input type="hidden" name="page" value="1">
        <input type="submit" value="Add to Cart & Go to Page 2">
    </form>
</body>
</html>


page2.html


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Page 2</title>
</head>
<body>
    <h2>Shopping Page 2</h2>
    
//////// <form method="post" action="http://localhost:8080/SessionInfo">
    <form method="post" action="ShoppingServlet">
        <label>Item C ($30): <input type="checkbox" name="item" value="30"></label><br>
        <label>Item D ($40): <input type="checkbox" name="item" value="40"></label><br>
        <input type="hidden" name="page" value="2">
        <input type="submit" value="Add to Cart & View Bill">
    </form>
</body>
</html>


ShoppingServlet.java
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ShoppingServlet")
public class ShoppingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int pageTotal = 0;

        // Retrieve selected items
        String[] items = req.getParameterValues("item");
        if (items != null) {
            for (String item : items) {
                pageTotal += Integer.parseInt(item);
            }
        }

        // Store the total for the current page
        String page = req.getParameter("page");
        session.setAttribute("page" + page + "Total", pageTotal);

        // Redirect to the next page or bill page
        if ("1".equals(page)) {
            res.sendRedirect("page2.html");
        } else {
            res.sendRedirect("BillServlet");
        }
    }
}





BillServlet.java

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int totalAmount = 0;

        // Retrieve totals from session
        Integer page1Total = (Integer) session.getAttribute("page1Total");
        Integer page2Total = (Integer) session.getAttribute("page2Total");

        if (page1Total != null) totalAmount += page1Total;
        if (page2Total != null) totalAmount += page2Total;

        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        pw.println("<html><body><h2>Final Bill</h2>");
        pw.println("<p>Page 1 Total: $" + (page1Total != null ? page1Total : 0) + "</p>");
        pw.println("<p>Page 2 Total: $" + (page2Total != null ? page2Total : 0) + "</p>");
        pw.println("<h3>Total Amount: $" + totalAmount + "</h3>");
        pw.println("</body></html>");

        // Clear session after purchase
        session.invalidate();
        pw.close();
    }
}



/YourProject
 ├── /src
 │    ├── ShoppingServlet.java
 │    ├── BillServlet.java
 │
 ├── /webapp
 │    ├── page1.html
 │    ├── page2.html
 │
 ├── /WEB-INF
 │    ├── classes (Compiled Java files go here)
