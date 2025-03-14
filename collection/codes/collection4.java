import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.*;

public class collection4 extends JFrame implements ActionListener
 {
    JTextField txtname, txtstd;
    JButton btnadd, btndelete, btnsearch;
    JPanel p1;

    Hashtable<String, String> table = new Hashtable<>();
    collection4()
     {
        setTitle("City STD Code Information");
        setSize(700, 500);
        setVisible(true);

        setLayout(new GridLayout(3, 2, 20, 20));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel name = new JLabel("Enter City Name: ");
        add(name);
        txtname = new JTextField(10);
        add(txtname);

        JLabel stdcode = new JLabel("Enter STD Code: ");
        add(stdcode);
        txtstd = new JTextField(10);
        add(txtstd);

        JLabel op = new JLabel("Choose Operation: ");
        add(op);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 3, 5, 5));

        btnadd = new JButton("Add");
        p1.add(btnadd);
        btnadd.addActionListener(this);
        btndelete = new JButton("Delete");
        p1.add(btndelete);
        btndelete.addActionListener(this);
        btnsearch = new JButton("Search");
        p1.add(btnsearch);
        btnsearch.addActionListener(this);
        add(p1);

    }
    @Override
    public void actionPerformed(ActionEvent ae)
     {
        String name = txtname.getText().trim();
        String std = txtstd.getText().trim();
        if (ae.getSource() == btnadd)
         {
               if (name.isEmpty() || std.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fields cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
               }
               if (table.containsKey(name) || table.containsValue(std)) 
               {
                JOptionPane.showMessageDialog(null,"Duplicates are not allowed", "Error", JOptionPane.ERROR_MESSAGE);
               } 
            else
             {
                table.put(name, std);
                JOptionPane.showMessageDialog(null, "Successfully Added City & STD Code", name,
                        JOptionPane.INFORMATION_MESSAGE);
              }
            txtname.setText("");
            txtstd.setText("");
        }
        if (ae.getSource() == btndelete) 
        {
           String s1 = JOptionPane.showInputDialog(null, "Enter City to remove");
           if (s1 != null && table.containsKey(s1)) {
            table.remove(s1);
            JOptionPane.showMessageDialog(null, "Successfully removed City & STD Code", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
           } else {
            JOptionPane.showMessageDialog(null, "City not found", "Error", JOptionPane.ERROR_MESSAGE);
           }
        }
        if (ae.getSource() == btnsearch)
         {
            String s1 = JOptionPane.showInputDialog(null, "Enter City");
            if (s1 != null && table.containsKey(s1))
             {
                String s2 = "STD Code: " + table.get(s1);
                JOptionPane.showMessageDialog(null, s2);
            }
             else 
             {
                JOptionPane.showMessageDialog(null, "City not found", "Error", JOptionPane.ERROR_MESSAGE);
           }
        }
    }
   public static void main(String[] args) 
   {
        new collection4();
    }
} 