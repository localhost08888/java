import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class JDBC6 extends JFrame implements ActionListener
 {
    JTextField courseNameField, instructorField, studentNameField, searchField;
    JTextArea displayArea;
    JButton addCourseBtn, addStudentBtn, deleteStudentBtn, viewStudentsBtn, searchStudentBtn;
    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;

    public JDBC6() 
    {
        setTitle("Course & Student Management");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        // UI Components
        add(new JLabel("Course Name:"));
        courseNameField = new JTextField();
        add(courseNameField);

        add(new JLabel("Instructor:"));
        instructorField = new JTextField();
        add(instructorField);

        addCourseBtn = new JButton("Add Course");
        addCourseBtn.addActionListener(this);
        add(addCourseBtn);

        add(new JLabel("Student Name:"));
        studentNameField = new JTextField();
        add(studentNameField);

        addStudentBtn = new JButton("Add Student");
        addStudentBtn.addActionListener(this);
        add(addStudentBtn);

        deleteStudentBtn = new JButton("Delete Student");
        deleteStudentBtn.addActionListener(this);
        add(deleteStudentBtn);

        viewStudentsBtn = new JButton("View All Students");
        viewStudentsBtn.addActionListener(this);
        add(viewStudentsBtn);

        add(new JLabel("Search Student:"));
        searchField = new JTextField();
        add(searchField);

        searchStudentBtn = new JButton("Search");
        searchStudentBtn.addActionListener(this);
        add(searchStudentBtn);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea));

        // Connect to Database
        connectDatabase();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void connectDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            conn=DriverManager.getConnection("jdbc:postgresql://192.168.0.12/tya13","tya13","Nilesh@3304");
            createTables();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed!");
        }
    }
    
    private void createTables() {
        try {
            Statement stmt = conn.createStatement();
            
            // Create Course table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Course (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "instructor VARCHAR(100) NOT NULL)"
            );
            
            // Create Student table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Student (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL)"
            );
            
            // Create Enrollment junction table for many-to-many relationship
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS Enrollment (" +
                "student_id INT REFERENCES Student(id) ON DELETE CASCADE, " +
                "course_id INT REFERENCES Course(id) ON DELETE CASCADE, " +
                "PRIMARY KEY (student_id, course_id))"
            );
            
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error creating tables: " + e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addCourseBtn) {
            addCourse();
        } else if (ae.getSource() == addStudentBtn) {
            addStudent();
        } else if (ae.getSource() == deleteStudentBtn) {
            deleteStudent();
        } else if (ae.getSource() == viewStudentsBtn) {
            viewAllStudents();
        } else if (ae.getSource() == searchStudentBtn) {
            searchStudent();
        }
    }

    private void addCourse() {
        try {
            String name = courseNameField.getText().trim();
            String instructor = instructorField.getText().trim();
            
            if (name.isEmpty() || instructor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields");
                return;
            }
            
            pstmt = conn.prepareStatement("INSERT INTO Course (name, instructor) VALUES (?, ?)");
            pstmt.setString(1, name);
            pstmt.setString(2, instructor);
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Course Added Successfully!");
                clearFields(courseNameField, instructorField);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Adding Course: " + e.getMessage());
        }
    }

    private void addStudent() {
        try {
            String name = studentNameField.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter student name");
                return;
            }
            
            pstmt = conn.prepareStatement("INSERT INTO Student (name) VALUES (?)");
            pstmt.setString(1, name);
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Student Added Successfully!");
                clearFields(studentNameField);
                viewAllStudents();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Adding Student: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            String name = studentNameField.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter student name");
                return;
            }
            
            // First delete from junction table
            pstmt = conn.prepareStatement("DELETE FROM Enrollment WHERE student_id IN (SELECT id FROM Student WHERE name = ?)");
            pstmt.setString(1, name);
            pstmt.executeUpdate();
            
            // Then delete from Student table
            pstmt = conn.prepareStatement("DELETE FROM Student WHERE name = ?");
            pstmt.setString(1, name);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Student Deleted Successfully!");
                clearFields(studentNameField);
                viewAllStudents();
            } else {
                JOptionPane.showMessageDialog(this, "Student Not Found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Deleting Student: " + e.getMessage());
        }
    }

    private void viewAllStudents() {
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Student ORDER BY id");
            
            displayArea.setText("Students:\n");
            
            while (rs.next()) {
                displayArea.append(rs.getInt("id") + " - " + rs.getString("name") + "\n");
            }
            
            if (displayArea.getText().equals("Students:\n")) {
                displayArea.append("No students found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Fetching Students: " + e.getMessage());
        }
    }

    private void searchStudent() {
        try {
            String name = searchField.getText().trim();
            
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter search term");
                return;
            }
            
            pstmt = conn.prepareStatement("SELECT * FROM Student WHERE name LIKE ? ORDER BY id");
            pstmt.setString(1, "%" + name + "%");
            
            rs = pstmt.executeQuery();
            
            displayArea.setText("Search Results:\n");
            
            while (rs.next()) {
                displayArea.append(rs.getInt("id") + " - " + rs.getString("name") + "\n");
            }
            
            if (displayArea.getText().equals("Search Results:\n")) {
                displayArea.append("No matching students found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Searching Student: " + e.getMessage());
        }
    }
    
    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static void main(String[] args) {
        new JDBC6();
    }
}




// CREATE TABLE Course (
//     id SERIAL PRIMARY KEY,
//     name VARCHAR(100) NOT NULL,
//     instructor VARCHAR(100) NOT NULL
// );

// CREATE TABLE Student (
//     id SERIAL PRIMARY KEY,
//     name VARCHAR(100) NOT NULL
// );

// CREATE TABLE Enrollment (
//     student_id INT REFERENCES Student(id) ON DELETE CASCADE,
//     course_id INT REFERENCES Course(id) ON DELETE CASCADE,
//     PRIMARY KEY (student_id, course_id)
// );
