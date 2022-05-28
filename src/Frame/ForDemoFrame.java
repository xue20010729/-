package Frame;

import helper.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ForDemoFrame extends JFrame {
    JButton borrowbutton;
    JTextField borrowerField;
    JTextField bookField;
    ForDemoFrame(){
        borrowbutton=new JButton("借阅");
        borrowerField=new JTextField();
        bookField=new JTextField();
        borrowbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int borrowerId=Integer.parseInt(borrowerField.getText());
                int bookId=Integer.parseInt(bookField.getText());
//                System.out.println(borrowerId);
//                System.out.println(bookId);
                try {
                    Controller.getController().borrowBookForDemo(borrowerId,bookId);
                } catch (SQLException ex) {
                    if(ex.getMessage().equals("not match condition")){
                        JOptionPane.showMessageDialog(null, "输入不符合条件！！","错误提示框！", JOptionPane.ERROR_MESSAGE);
                    }
                    ex.printStackTrace();
                }
            }
        });
        setLayout(new GridLayout(1,3));
        add(borrowerField);
        add(bookField);
        add(borrowbutton);
        setTitle("demo");
        setBounds(300, 200, 600,200);
    }
}
