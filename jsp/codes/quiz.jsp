<%@ page import="java.sql.*, java.util.*" %>
<html>
<head>
    <title>Online Quiz</title>
</head>
<body>

<%
   
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://192.168.0.12/tya13", "tya13", "Nilesh@3304");

        // Session Handling
        Integer index = (Integer) session.getAttribute("index");
        if (index == null) index = 0;

        Integer score = (Integer) session.getAttribute("score");
        if (score == null) score = 0;

        List<Integer> questionIDs = (List<Integer>) session.getAttribute("questionIDs");
        if (questionIDs == null) {
            questionIDs = new ArrayList<>();
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT id FROM questions");
            while (rs.next()) {
                questionIDs.add(rs.getInt("id"));
            }
            Collections.shuffle(questionIDs);
            session.setAttribute("questionIDs", questionIDs);
        }

        if (index >= questionIDs.size()) 
        {
            out.println("<h2>Quiz Completed!</h2>");
            out.println("<p>Your Score: " + score + "/" + questionIDs.size() + "</p>");
            out.println("<a href='quiz.jsp'>Try Again</a>");
            session.invalidate();
            conn.close();
            return;
        }

        
        int qID = questionIDs.get(index);
        ps = conn.prepareStatement("SELECT * FROM questions WHERE id=?");
        ps.setInt(1, qID);
        rs = ps.executeQuery();

        String question = "";
        String[] options = new String[4];
        int correctAnswer = -1;
        if (rs.next())
        {
            question = rs.getString("question");
            options[0] = rs.getString("option1");
            options[1] = rs.getString("option2");
            options[2] = rs.getString("option3");
            options[3] = rs.getString("option4");
            correctAnswer = rs.getInt("correct_option");
        }

        session.setAttribute("correctAnswer", correctAnswer);
    %>

    <h2>Question <%= index + 1 %></h2>
    <form action="quiz.jsp" method="post">
        <p><%= question %></p>
        <% for (int i = 0; i < 4; i++) { %>
            <input type="radio" name="answer" value="<%= (i + 1) %>"> <%= options[i] %><br>
        <% } %>
        <input type="submit" value="Next">
    </form>

    <%
        if ("POST".equalsIgnoreCase(request.getMethod()))
         {
            String userAnswer = request.getParameter("answer");
            if (userAnswer != null && Integer.parseInt(userAnswer) == correctAnswer) 
            {
                score++;
            }
            session.setAttribute("score", score);
            session.setAttribute("index", index + 1);
            response.sendRedirect("quiz.jsp");
        }
    } 
    catch (Exception e) 
    {
        out.println("<p>Error: " + e.getMessage() + "</p>");
    } 
    finally 
    {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (conn != null) conn.close();
    }
%>

</body>
</html>




<!-- CREATE TABLE questions (
    id SERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    option1 VARCHAR(255) NOT NULL,
    option2 VARCHAR(255) NOT NULL,
    option3 VARCHAR(255) NOT NULL,
    option4 VARCHAR(255) NOT NULL,
    correct_option INT NOT NULL
);

-- Insert Sample Questions
INSERT INTO questions (question, option1, option2, option3, option4, correct_option) VALUES
('What is the capital of France?', 'London', 'Berlin', 'Paris', 'Rome', 3),
('Which programming language is used for Android development?', 'Python', 'Java', 'C++', 'Ruby', 2),
('Who developed the theory of relativity?', 'Newton', 'Einstein', 'Galileo', 'Tesla', 2); -->
