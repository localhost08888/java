import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class JDBC7 extends JFrame implements ActionListener {
    JTextField nameField, phoneField, callsField, monthField, yearField, searchField;
    JTextArea displayArea;
    JButton addUserBtn, searchUserBtn, calculateBillBtn;
    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;

    public JDBC7() {
        setTitle("Telephone Billing System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2));

        // UI Components
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Phone Number:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Number of Calls:"));
        callsField = new JTextField();
        add(callsField);

        add(new JLabel("Month:"));
        monthField = new JTextField();
        add(monthField);

        add(new JLabel("Year:"));
        yearField = new JTextField();
        add(yearField);

        addUserBtn = new JButton("Add User");
        addUserBtn.addActionListener(this);
        add(addUserBtn);

        add(new JLabel("Search by Name/Phone:"));
        searchField = new JTextField();
        add(searchField);

        searchUserBtn = new JButton("Search User");
        searchUserBtn.addActionListener(this);
        add(searchUserBtn);

        calculateBillBtn = new JButton("Calculate Bill");
        calculateBillBtn.addActionListener(this);
        add(calculateBillBtn);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea));

        connectDatabase();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void connectDatabase() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://192.168.0.12/examx", "examx", "examxpass");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed!");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addUserBtn) {
            addUser();
        } else if (ae.getSource() == searchUserBtn) {
            searchUser();
        } else if (ae.getSource() == calculateBillBtn) {
            calculateBill();
        }
    }

    private void addUser() {
        try {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String calls = callsField.getText().trim();
            String month = monthField.getText().trim();
            String year = yearField.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || calls.isEmpty() || month.isEmpty() || year.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            int numCalls = Integer.parseInt(calls);
            int yearValue = Integer.parseInt(year);

            pstmt = conn.prepareStatement("INSERT INTO TelephoneUser (name, phone, calls, month, year) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setInt(3, numCalls);
            pstmt.setString(4, month);
            pstmt.setInt(5, yearValue);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "User Added Successfully!");
                clearFields(nameField, phoneField, callsField, monthField, yearField);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Adding User: " + e.getMessage());
        }
    }

    private void searchUser() {
        try {
            String query = searchField.getText().trim();
            
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a name or phone number!");
                return;
            }

            pstmt = conn.prepareStatement("SELECT * FROM TelephoneUser WHERE name LIKE ? OR phone LIKE ?");
            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");

            rs = pstmt.executeQuery();
            displayArea.setText("Search Results:\n");

            while (rs.next()) {
                displayArea.append("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Phone: " + rs.getString("phone") +
                        ", Calls: " + rs.getInt("calls") +
                        ", Month: " + rs.getString("month") +
                        ", Year: " + rs.getInt("year") + "\n");
            }

            if (displayArea.getText().equals("Search Results:\n")) {
                displayArea.append("No matching records found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Searching User: " + e.getMessage());
        }
    }

    private void calculateBill() {
        try {
            String query = searchField.getText().trim();
            
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a name or phone number!");
                return;
            }

            pstmt = conn.prepareStatement("SELECT * FROM TelephoneUser WHERE name LIKE ? OR phone LIKE ?");
            pstmt.setString(1, "%" + query + "%");
            pstmt.setString(2, "%" + query + "%");

            rs = pstmt.executeQuery();
            displayArea.setText("Bill Details:\n");

            while (rs.next()) {
                int calls = rs.getInt("calls");
                double totalBill = calculateTotalBill(calls);

                displayArea.append("ID: " + rs.getInt("id") +
                        ", Name: " + rs.getString("name") +
                        ", Phone: " + rs.getString("phone") +
                        ", Calls: " + calls +
                        ", Month: " + rs.getString("month") +
                        ", Year: " + rs.getInt("year") +
                        ", Bill: Rs. " + totalBill + "\n");
            }

            if (displayArea.getText().equals("Bill Details:\n")) {
                displayArea.append("No records found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Calculating Bill: " + e.getMessage());
        }
    }

    private double calculateTotalBill(int calls) {
        double rent = 300;
        double cost = 0;

        if (calls > 100 && calls <= 500) {
            cost = (calls - 100) * 1.00;
        } else if (calls > 500) {
            cost = (400 * 1.00) + ((calls - 500) * 1.30);
        }

        return rent + cost;
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    public static void main(String[] args) {
        new JDBC7();
    }
}


// CREATE DATABASE tya13;
// \c tya13


// CREATE TABLE TelephoneUser (
//     id SERIAL PRIMARY KEY,
//     name VARCHAR(100) NOT NULL,
//     phone VARCHAR(15) UNIQUE NOT NULL,
//     calls INT NOT NULL,
//     month VARCHAR(20) NOT NULL,
//     year INT NOT NULL
// );
