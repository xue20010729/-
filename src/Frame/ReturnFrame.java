package Frame;

import allClass.BorrowInfo;
import helper.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnFrame extends JFrame {
    private JTable borrowInfoTable;
    private JButton returnButton;
    public static int state =0;
    ReturnFrame(){

    }

    public void setBorrowInfoTable(List<BorrowInfo> allBorrowInfo){
        Container contentPane=getContentPane();
        String[] name = {"书籍名称","借阅人","开始时间","结束时间","选择"};
        Object[][] borrowInfo = new Object[allBorrowInfo.size()][5];
        for(int i=0;i<allBorrowInfo.size();i++){
            borrowInfo[i][0]=allBorrowInfo.get(i).getBookName();
            borrowInfo[i][1]=allBorrowInfo.get(i).getBorrowName();
            borrowInfo[i][2]=allBorrowInfo.get(i).getStartTime();
            borrowInfo[i][3]=allBorrowInfo.get(i).getEndTime();
            borrowInfo[i][4]=false;
        }

        DefaultTableModel model = new DefaultTableModel(borrowInfo, name);
        borrowInfoTable = new JTable(model) {
            public Class getColumnClass(int column) {
                //return Boolean.class
                return getValueAt(0, column).getClass();
            }
            public boolean isCellEditable(int row, int column) {
                if(column==4){
                    return true;
                }
                return false;
            }
        };

        returnButton=new JButton("归还");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(num);
                List<Integer> selectedBorrowInfo =new ArrayList<Integer>();

                for (int i = 0; i < borrowInfoTable.getRowCount(); i++) {
//                    System.out.println(bookTable.getValueAt(i,4));
                    if ((boolean) borrowInfoTable.getValueAt(i, 4)) {
                        String bookName = (String) borrowInfoTable.getValueAt(i,0);
                        selectedBorrowInfo.add(i);
                        try {
                            Controller.getController().someoneWantToReturnBook(bookName);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
//                        System.out.println(bookName);
//                        System.out.println(borrowerName);
                    }
                }
                new Thread(() -> {
                    refreshTable(selectedBorrowInfo);
                }).start();


            }
        });
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(borrowInfoTable),BorderLayout.CENTER);
        contentPane.add(returnButton,BorderLayout.SOUTH);
        setTitle("书籍总表");
        setSize(600,400);
        setVisible(true);
    }

    public void refreshTable(List<Integer> selectedRows){
        DefaultTableModel model = (DefaultTableModel) borrowInfoTable.getModel();//获取defaulttablemodel
        for (Integer selectedRow : selectedRows) {
            model.removeRow(selectedRow);
        }
    }

    public void resetTable(List<BorrowInfo> allBorrowInfo){
        DefaultTableModel model = (DefaultTableModel) borrowInfoTable.getModel();
        for(int i=0;i<borrowInfoTable.getRowCount();i++){
            model.removeRow(i);
        }
        Object[][] borrowInfo = new Object[allBorrowInfo.size()][5];
        for(int i=0;i<allBorrowInfo.size();i++){
            borrowInfo[i][0]=allBorrowInfo.get(i).getBookName();
            borrowInfo[i][1]=allBorrowInfo.get(i).getBorrowName();
            borrowInfo[i][2]=allBorrowInfo.get(i).getStartTime();
            borrowInfo[i][3]=allBorrowInfo.get(i).getEndTime();
            borrowInfo[i][4]=false;
        }
        for (int i=0;i<allBorrowInfo.size();i++){
            model.addRow(borrowInfo[i]);
        }
    }

}
