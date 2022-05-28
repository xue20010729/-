package Frame;

import helper.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class WelcomeFrame extends JFrame{
    private JButton logInButton;
    private JButton administratorLogInButton;
//    private JButton onlineButton;
    WelcomeFrame(){
        logInButton =new JButton("Log In");
        administratorLogInButton =new JButton("Demo");
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getController().showLogInFrame();
            }
        });
        administratorLogInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getController().showForDemoFrame();
            }
        });
        setLayout(new GridLayout(3,1));
        add(new JLabel("                                      管理系统"));
        add(logInButton);
        add(administratorLogInButton);
        setTitle("管理系统");
        setBounds(300, 200, 300,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setVisible(true);
    }

    public static void main(String[] args) {
        WelcomeFrame test =new WelcomeFrame();
    }
}

