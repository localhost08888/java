// CREATE TABLE phonebook (
//     id SERIAL PRIMARY KEY,
//     name VARCHAR(100) NOT NULL,
//     address TEXT,
//     phone BIGINT UNIQUE NOT NULL,
//     email VARCHAR(255) UNIQUE NOT NULL
// );
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class JDBC5 extends JFrame implements ActionListener
 {
    JLabel[] labels = {new JLabel("ID"), new JLabel("Name"), new JLabel("Address"), new JLabel("Phone"), new JLabel("Email")};
    
    JTextField[] fields = {new JTextField(), new JTextField(), new JTextField(), new JTextField(), new JTextField()};
    
    JButton[] buttons = {new JButton("<<"), new JButton("DELETE"), new JButton("UPDATE"), new JButton("SAVE"), new JButton(">>"), new JButton("EXIT")};
    
    Connection conn;
    PreparedStatement pstmt;
    ResultSet rs;

    public JDBC5()
     {
        setLayout(null);
        JLabel hl = new JLabel("Phone-Book");
        hl.setBounds(230, 2, 100, 30);
        add(hl);

        for (int i = 0; i < labels.length; i++)
         {
            labels[i].setBounds(30, 30 + i * 40, 100, 30);
            fields[i].setBounds(140, 30 + i * 40, 350, 30);
            add(labels[i]);
            add(fields[i]);
        }

        int x = 30;
        for (JButton button : buttons)
         {
            button.setBounds(x, 230, 100, 30);
            button.addActionListener(this);
            add(button);
            x += 110;
        }
        buttons[3].setEnabled(false);

        setTitle("Phone Book");
        setSize(600, 350);
        setLocation(170, 100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        try
         {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://192.168.0.12/examx", "examx", "examxpass");
        }
         catch (Exception e) 
         {
            JOptionPane.showMessageDialog(this, "Database connection failed!");
        }
    }

    public void actionPerformed(ActionEvent ae) 
    {
        if (ae.getSource() == buttons[0])
         fetchRecord("<");
        else if (ae.getSource() == buttons[1])
         deleteRecord();
        else if (ae.getSource() == buttons[2])
         buttons[3].setEnabled(true);
        else if (ae.getSource() == buttons[3])
         updateRecord();
        else if (ae.getSource() == buttons[4]) 
        fetchRecord(">");
        else if (ae.getSource() == buttons[5])
         System.exit(0);
    }

    public void fetchRecord(String direction)
     {
        try 
        {
            int id = Integer.parseInt(fields[0].getText());
            String query = "SELECT * FROM phonebook WHERE id " + (direction.equals("<") ? "<" : ">") + " ? ORDER BY id " + (direction.equals("<") ? "DESC" : "ASC") + " LIMIT 1";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next())
             displayRecord(rs);
            else JOptionPane.showMessageDialog(this, "No record found");
        } 
        catch (Exception e)
         {
            JOptionPane.showMessageDialog(this, "Invalid ID");
        }
    }

    public void deleteRecord()
     {
        try
         {
            pstmt = conn.prepareStatement("DELETE FROM phonebook WHERE id = ?");
            pstmt.setInt(1, Integer.parseInt(fields[0].getText()));
            if (pstmt.executeUpdate() > 0)
             {
                JOptionPane.showMessageDialog(this, "Record deleted");
                clearFields();
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(this, "Error deleting record");
        }
    }

    public void updateRecord()
     {
        try
         {
            pstmt = conn.prepareStatement("UPDATE phonebook SET name=?, address=?, phone=?, email=? WHERE id=?");
            for (int i = 1; i < fields.length; i++) pstmt.setString(i, fields[i].getText());
            pstmt.setInt(5, Integer.parseInt(fields[0].getText()));
            if (pstmt.executeUpdate() > 0) JOptionPane.showMessageDialog(this, "Record updated");
            buttons[3].setEnabled(false);
        }
         catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating record");
        }
    }

    public void displayRecord(ResultSet rs) throws SQLException 
    {
        for (int i = 0; i < fields.length; i++)
          fields[i].setText(rs.getString(i + 1));
    }

    public void clearFields() 
    {
        for (JTextField field : fields) field.setText("");
    }

    public static void main(String[] args)
     {
        new JDBC5();
    }
}
// import java.awt.event.*;
// import java.sql.*;
// import javax.swing.*;

// public class JDBC5 extends JFrame implements ActionListener
//  {
//     JLabel jl1, jl2, jl3, jl4, jl5, hl;
//     JTextField jt1, jt2, jt3, jt4, jt5;
//     JButton b1, b2, b3, b4, b5, b6;

//     Connection conn;
//     PreparedStatement pstmt;
//     ResultSet rs;

//     public JDBC5()
//     {
//         setLayout(null);

//         hl = new JLabel("Phone-Book");
//         hl.setSize(100, 30);
//         hl.setLocation(230, 2);
//         add(hl);

//         jl1 = new JLabel("ID");
//         jl1.setSize(100, 30);
//         jl1.setLocation(30, 30);
//         add(jl1);
//         jt1 = new JTextField();
//         jt1.setSize(350, 30);
//         jt1.setLocation(140, 30);
//         add(jt1);

//         jl2 = new JLabel("Name");
//         jl2.setSize(100, 30);
//         jl2.setLocation(30, 70);
//         add(jl2);
//         jt2 = new JTextField();
//         jt2.setSize(350, 30);
//         jt2.setLocation(140, 70);
//         add(jt2);

//         jl3 = new JLabel("Address");
//         jl3.setSize(100, 30);
//         jl3.setLocation(30, 110);
//         add(jl3);
//         jt3 = new JTextField();
//         jt3.setSize(350, 30);
//         jt3.setLocation(140, 110);
//         add(jt3);

//         jl4 = new JLabel("Phone");
//         jl4.setSize(100, 30);
//         jl4.setLocation(30, 150);
//         add(jl4);
//         jt4 = new JTextField();
//         jt4.setSize(350, 30);
//         jt4.setLocation(140, 150);
//         add(jt4);

//         jl5 = new JLabel("Email");
//         jl5.setSize(100, 30);
//         jl5.setLocation(30, 190);
//         add(jl5);
//         jt5 = new JTextField();
//         jt5.setSize(350, 30);
//         jt5.setLocation(140, 190);
//         add(jt5);

//         b1 = new JButton("<<");
//         b1.setSize(100, 30);
//         b1.setLocation(30, 230);
//         add(b1);
//         b1.addActionListener(this);

//         b2 = new JButton("DELETE");
//         b2.setSize(100, 30);
//         b2.setLocation(140, 230);
//         add(b2);
//         b2.addActionListener(this);

//         b3 = new JButton("UPDATE");
//         b3.setSize(100, 30);
//         b3.setLocation(250, 230);
//         add(b3);
//         b3.addActionListener(this);

//         b6 = new JButton("SAVE");
//         b6.setSize(70, 30);
//         b6.setLocation(270, 270);
//         b6.setEnabled(false);
//         add(b6);
//         b6.addActionListener(this);

//         b4 = new JButton(">>");
//         b4.setSize(100, 30);
//         b4.setLocation(360, 230);
//         add(b4);
//         b4.addActionListener(this);

//         b5 = new JButton("EXIT");
//         b5.setSize(100, 30);
//         b5.setLocation(470, 230);
//         add(b5);
//         b5.addActionListener(this);

//         setTitle("Phone Book");
//         setSize(600, 350);
//         setLocation(170, 100);
//         setVisible(true);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         try
//          {
//             Class.forName("org.postgresql.Driver");
//             conn=DriverManager.getConnection("jdbc:postgresql://192.168.0.12/tya13","tya13","Nilesh@3304");
//         } 
//         catch (Exception e) 
//         {
//             JOptionPane.showMessageDialog(this, "Database connection failed!");
//         }
//     }

//     public void actionPerformed(ActionEvent ae)
//      {
//         if (ae.getSource() == b1) {
//             fetchPreviousRecord();
//         } else if (ae.getSource() == b2) {
//             deleteRecord();
//         } else if (ae.getSource() == b3) {
//             b6.setEnabled(true);
//         } else if (ae.getSource() == b6) {
//             saveUpdateRecord();
//         } else if (ae.getSource() == b4) {
//             fetchNextRecord();
//         } else if (ae.getSource() == b5) {
//             System.exit(0);
//         }
//     }

//     public void fetchPreviousRecord()
//      {
//         try
//          {
//             int id = Integer.parseInt(jt1.getText());
//             pstmt = conn.prepareStatement("SELECT * FROM phonebook WHERE id < ? ORDER BY id DESC LIMIT 1");
//             pstmt.setInt(1, id);
//             rs = pstmt.executeQuery();
//             if (rs.next()) 
//             {
//                 displayRecord(rs);
//                 b6.setEnabled(false);
//             }
//              else
//               {
//                 JOptionPane.showMessageDialog(this, "No previous record");
//             }
//         } 
//         catch (Exception e)
//          {
//             // Error handling
//         }
//     }

//     public void fetchNextRecord() 
//     {
//         try 
//         {
//             int id = Integer.parseInt(jt1.getText());
//             pstmt = conn.prepareStatement("SELECT * FROM phonebook WHERE id > ? ORDER BY id ASC LIMIT 1");
//             pstmt.setInt(1, id);
//             rs = pstmt.executeQuery();
//             if (rs.next())
//              {
//                 displayRecord(rs);
//                 b6.setEnabled(false);
//             }
//              else
//               {
//                 JOptionPane.showMessageDialog(this, "No next record");
//             }
//         } 
//         catch (Exception e)
//          {
//             // Error handling
//         }
//     }

//     public void deleteRecord()
//      {
//         try
//          {
//             int id = Integer.parseInt(jt1.getText());
//             pstmt = conn.prepareStatement("DELETE FROM phonebook WHERE id = ?");
//             pstmt.setInt(1, id);
//             int rowsAffected = pstmt.executeUpdate();
//             if (rowsAffected > 0)
//              {
//                 JOptionPane.showMessageDialog(this, "Record deleted");
//                 clearFields();
//             }
//         }
//          catch (Exception e) 
//          {
//             // Error handling
//         }
//     }

//     public void saveUpdateRecord()
//      {
//         try {
//             int id = Integer.parseInt(jt1.getText());
//             String name = jt2.getText().trim();
//             String address = jt3.getText().trim();
//             long phone = Long.parseLong(jt4.getText().trim());
//             String email = jt5.getText().trim();

//             pstmt = conn.prepareStatement("UPDATE phonebook SET name=?, address=?, phone=?, email=? WHERE id=?");
//             pstmt.setString(1, name);
//             pstmt.setString(2, address);
//             pstmt.setLong(3, phone);
//             pstmt.setString(4, email);
//             pstmt.setInt(5, id);

//             int rowsAffected = pstmt.executeUpdate();
//             if (rowsAffected > 0) 
//             {
//                 JOptionPane.showMessageDialog(this, "Record updated");
//                 b6.setEnabled(false);
//             }
//         } catch (Exception e)
//          {
//             // Error handling
//         }
//     }

//     public void displayRecord(ResultSet rs) throws SQLException
//      {
//         jt1.setText(String.valueOf(rs.getInt("id")));
//         jt2.setText(rs.getString("name"));
//         jt3.setText(rs.getString("address"));
//         jt4.setText(rs.getString("phone"));
//         jt5.setText(rs.getString("email"));
//     }

//     public void clearFields()
//      {
//         jt1.setText("");
//         jt2.setText("");
//         jt3.setText("");
//         jt4.setText("");
//         jt5.setText("");
//     }

//     public static void main(String[] args)
//      {
//         new JDBC5();
//     }
// }
