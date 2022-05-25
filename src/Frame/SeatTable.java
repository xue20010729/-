package Frame;

import allClass.Seat;
import helper.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatTable extends JFrame {
    private JTable seatTable;
    private JButton reseverButton;
    public static int state =0;
    public SeatTable(){

    }

    public void setSeatData(List<Seat> allSeats){
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane=getContentPane();
        String[] name = {"座位编号","楼层","选择"};
        Object[][] bookInfo = new Object[allSeats.size()][3];
        for(int i=0;i<allSeats.size();i++){
            bookInfo[i][0]=allSeats.get(i).getSeatId();
            bookInfo[i][1]=allSeats.get(i).getFloor();
            bookInfo[i][2]=false;
        }

        DefaultTableModel model = new DefaultTableModel(bookInfo, name);
        seatTable = new JTable(model) {
            public Class getColumnClass(int column) {
                //return Boolean.class
                return getValueAt(0, column).getClass();
            }
            public boolean isCellEditable(int row, int column) {
                if(column==2){
                    return true;
                }
                return false;
            }
        };

        reseverButton=new JButton("查询");
        reseverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> selectedSeatId =new ArrayList<Integer>();
                for (int i=0;i<seatTable.getRowCount();i++){
                    if((boolean)seatTable.getValueAt(i,2)){
                        selectedSeatId.add((Integer) model.getValueAt(i,0));
                    }
                }
                if(selectedSeatId.size() != 1) {
                    JOptionPane.showConfirmDialog(null, "选择的数量不符合规则", "ConfirmDialog", JOptionPane.DEFAULT_OPTION);
                }else {
//                    System.out.println("准备查询");
//                    System.out.println(selectedSeatId.get(0));
                    try {
                        Controller.getController().showOneSeatInfo(selectedSeatId.get(0));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(seatTable),BorderLayout.CENTER);
        contentPane.add(reseverButton,BorderLayout.SOUTH);
        setTitle("书籍总表");
        setSize(600,200);
        setVisible(true);
    }
}
