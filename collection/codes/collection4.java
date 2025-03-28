 import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import javax.swing.*;

public class collection4 extends JFrame implements ActionListener
 {
    JTextField txtname, txtstd;
    JButton btnadd, btndelete, btnsearch;
    Hashtable<String, String> table = new Hashtable<>();

    collection4()
     {
        setTitle("City STD Code Information");
        setSize(400, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Enter City Name:"));
        txtname = new JTextField();
        add(txtname);

        add(new JLabel("Enter STD Code:"));
        txtstd = new JTextField();
        add(txtstd);

        JPanel p1 = new JPanel(new GridLayout(1, 3, 5, 5));
        btnadd = new JButton("Add");
        btnadd.addActionListener(this);
        p1.add(btnadd);
        
        btndelete = new JButton("Delete");
        btndelete.addActionListener(this);
        p1.add(btndelete);
        
        btnsearch = new JButton("Search");
        btnsearch.addActionListener(this);
        p1.add(btnsearch);
        
        add(p1);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
     {
        String name = txtname.getText().trim();
        String std = txtstd.getText().trim();

        if (ae.getSource() == btnadd)
         {
               if (name.isEmpty() || std.isEmpty() || table.containsKey(name))
               {
                  JOptionPane.showMessageDialog(this, "Invalid input or duplicate entry", "Error", JOptionPane.ERROR_MESSAGE);
               } 
               else 
               {
                  table.put(name, std);
                  JOptionPane.showMessageDialog(this, "Successfully Added", "Success", JOptionPane.INFORMATION_MESSAGE);
                  txtname.setText("");
                  txtstd.setText("");
               }
        } 
        else if (ae.getSource() == btndelete)
         {
            String city = JOptionPane.showInputDialog(this, "Enter City to remove");
            if (table.remove(city) != null) 
            {
                JOptionPane.showMessageDialog(this, "City Removed", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
             else 
             {
                JOptionPane.showMessageDialog(this, "City not found", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } 
        else if (ae.getSource() == btnsearch) 
        {
            String city = JOptionPane.showInputDialog(this, "Enter City");
            String code = table.get(city);
            JOptionPane.showMessageDialog(this, code != null ? "STD Code: " + code : "City not found", "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args)
     {
        new collection4();
    }
}
