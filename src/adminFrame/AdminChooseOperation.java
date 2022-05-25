package adminFrame;

import helper.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminChooseOperation extends JFrame {
    private JButton addBookButton;
    private JButton checkBorrowHistoryButton;
    AdminChooseOperation(){
        addBookButton =new JButton("添加书籍");
//        checkBorrowHistoryButton =new JButton("");
        addBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                try {
//                    Controller.getController().showBookTable();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
            }
        });
//        checkBorrowHistoryButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    Controller.getController().showReturnFrame();
//                } catch (SQLException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
        setLayout(new GridLayout(1,1));
        add(addBookButton);
//        add(checkBorrowHistoryButton);
        setTitle("功能选择");
        setBounds(300, 200, 300,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
