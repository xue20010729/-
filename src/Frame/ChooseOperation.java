package Frame;

import helper.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ChooseOperation extends JFrame {
    private JButton borrowButton;
    private JButton returnButton;
    private JButton reseverButton;
    private JButton releaseBUtton;
    ChooseOperation(){
        borrowButton =new JButton("借阅");
        returnButton=new JButton("归还");
        reseverButton=new JButton("预定座位");
        releaseBUtton=new JButton("释放座位");
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Controller.getController().showBookTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        reseverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Controller.getController().showSeatTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Controller.getController().showReturnFrame();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setLayout(new GridLayout(1,4));
        add(borrowButton);
        add(returnButton);
        add(reseverButton);
        add(releaseBUtton);
        setTitle("功能选择");
        setBounds(300, 200, 300,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
