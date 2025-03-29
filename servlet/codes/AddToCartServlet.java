public import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddToCartServlet")
public class AddToCartServlet extends HttpServlet 
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
             {
        HttpSession session = request.getSession();

        
        
        Integer page1Total = (Integer) session.getAttribute("page1Total");
        Integer page2Total = (Integer) session.getAttribute("page2Total");

        if (page1Total == null) page1Total = 0;
        if (page2Total == null) page2Total = 0;

       
        
        String page = request.getParameter("page");
        int total = 0;
        String[] selectedItems = request.getParameterValues("items");

        if (selectedItems != null)
         {
            for (String price : selectedItems) 
            {
                total += Integer.parseInt(price);
            }
        }

        
        
        if ("1".equals(page))
        {
            session.setAttribute("page1Total", page1Total + total);
            response.sendRedirect("page2.jsp");
        } 
        else if ("2".equals(page)) 
        {
            session.setAttribute("page2Total", page2Total + total);
            response.sendRedirect("bill.jsp");
        }
    }
}
  

page2.jsp

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>Shopping Mall - Page 2</title></head>
<body>
    <h2>Shopping Page 2</h2>
    <form action="AddToCartServlet" method="post">
        <input type="checkbox" name="items" value="250"> Item D - $250 <br>
        <input type="checkbox" name="items" value="300"> Item E - $300 <br>
        <input type="checkbox" name="items" value="180"> Item F - $180 <br>
        <input type="hidden" name="page" value="2">
        <input type="submit" value="View Bill">
    </form>
</body>
</html>



bill.jsp

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head><title>Shopping Bill</title></head>
<body>
    <h2>Final Bill</h2>
    <%
        Integer page1Total = (Integer) session.getAttribute("page1Total");
        Integer page2Total = (Integer) session.getAttribute("page2Total");
        if (page1Total == null) page1Total = 0;
        if (page2Total == null) page2Total = 0;
        int grandTotal = page1Total + page2Total;
    %>
    <p>Page 1 Total: $<%= page1Total %></p>
    <p>Page 2 Total: $<%= page2Total %></p>
    <h3>Grand Total: $<%= grandTotal %></h3>
</body>
</html>
