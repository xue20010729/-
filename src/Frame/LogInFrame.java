package Frame;

import helper.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LogInFrame extends JFrame {
    JTextField userName;
    JTextField passwordName;
    JButton logInButton;
    LogInFrame(){
        userName =new JTextField();
        passwordName =new JTextField();
        logInButton =new JButton("登录");
        userName.setText("xuechen");
        passwordName.setText("123456");
        userName.setHorizontalAlignment(JTextField.CENTER);
        passwordName.setHorizontalAlignment(JTextField.CENTER);
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userName.getText();
                String password =passwordName.getText();
                try {
                    Controller.getController().userLogIn(user,password);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Container contentPane=getContentPane();
        contentPane.setLayout(new GridLayout(3,1));
        contentPane.add(userName);
        contentPane.add(passwordName);
        contentPane.add(logInButton);
        setTitle("登录");
        setBounds(300, 200, 300,400);
    }
}
