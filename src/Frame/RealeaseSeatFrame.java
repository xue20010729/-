package Frame;

import allClass.BorrowInfo;
import allClass.ReserveInfo;
import helper.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RealeaseSeatFrame extends JFrame {
    private JTable reserveInfoTable;
    private JButton realeaseButton;
    public static int state =0;
    RealeaseSeatFrame(){

    }
    public void setReserveInfoTable(List<ReserveInfo> allReserveInfo){
        Container contentPane=getContentPane();
        String[] name = {"借阅人","座位编号","开始时间","结束时间","日期","选择"};
        Object[][] reserveInfos = new Object[allReserveInfo.size()][6];
        for(int i=0;i<allReserveInfo.size();i++){
            reserveInfos[i][0]=allReserveInfo.get(i).getBorrowerId();
            reserveInfos[i][1]=allReserveInfo.get(i).getSeatId();
            reserveInfos[i][2]=allReserveInfo.get(i).getStart();
            reserveInfos[i][3]=allReserveInfo.get(i).getEnd();
            reserveInfos[i][4]=allReserveInfo.get(i).getDate();
            reserveInfos[i][5]=false;
        }

        DefaultTableModel model = new DefaultTableModel(reserveInfos, name);
        reserveInfoTable = new JTable(model) {
            public Class getColumnClass(int column) {
                //return Boolean.class
                return getValueAt(0, column).getClass();
            }
            public boolean isCellEditable(int row, int column) {
                if(column==5){
                    return true;
                }
                return false;
            }
        };

        realeaseButton=new JButton("释放");
        realeaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(num);
                List<Integer> selectedReserverInfo =new ArrayList<Integer>();

                for (int i = 0; i < reserveInfoTable.getRowCount(); i++) {
//                    System.out.println(bookTable.getValueAt(i,4));
                    if ((boolean) reserveInfoTable.getValueAt(i, 5)) {
                        int seatId = (int) reserveInfoTable.getValueAt(i,1);
                        selectedReserverInfo.add(i);
//                        System.out.println(seatId);
                        try {
                            Controller.getController().realeaseSeat(seatId);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
//                        try {
//                            Controller.getController().someoneWantToReturnBook(bookName);
//                        } catch (SQLException ex) {
//                            ex.printStackTrace();
//                        }
//                        System.out.println(bookName);
//                        System.out.println(borrowerName);
                    }
                }
                new Thread(() -> {
                    refreshTable(selectedReserverInfo);
                }).start();


            }
        });
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(reserveInfoTable),BorderLayout.CENTER);
        contentPane.add(realeaseButton,BorderLayout.SOUTH);
        setTitle("预定记录");
        setSize(600,400);
        setVisible(true);
    }

    public void refreshTable(List<Integer> selectedRows){
        DefaultTableModel model = (DefaultTableModel) reserveInfoTable.getModel();//获取defaulttablemodel
        for (Integer selectedRow : selectedRows) {
            model.removeRow(selectedRow);
        }
    }

    public void resetTable(List<ReserveInfo> allReserveInfo){
        DefaultTableModel model = (DefaultTableModel) reserveInfoTable.getModel();
        for(int i=0;i<reserveInfoTable.getRowCount();i++){
            model.removeRow(i);
        }
        Object[][] reserveInfos = new Object[allReserveInfo.size()][6];
        for(int i=0;i<allReserveInfo.size();i++){
            reserveInfos[i][0]=allReserveInfo.get(i).getBorrowerId();
            reserveInfos[i][1]=allReserveInfo.get(i).getSeatId();
            reserveInfos[i][2]=allReserveInfo.get(i).getStart();
            reserveInfos[i][3]=allReserveInfo.get(i).getEnd();
            reserveInfos[i][4]=allReserveInfo.get(i).getDate();
            reserveInfos[i][5]=false;
        }

        for (int i=0;i<allReserveInfo.size();i++){
            model.addRow(reserveInfos[i]);
        }
    }

}
