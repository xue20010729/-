package Frame;

import allClass.book;
import com.sun.org.apache.xpath.internal.objects.XObject;
import helper.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookTable extends JFrame{

    private JTable bookTable;
    private JButton borrowButton;
    public static int state = 0;
    BookTable(){

    }

    public void setTableData(List<book> allBook){
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane=getContentPane();
        String[] name = {"书籍编号","书籍名称","可借数量","楼层","选择"};
        Object[][] bookInfo = new Object[allBook.size()][5];
        for(int i=0;i<allBook.size();i++){
            bookInfo[i][0]=allBook.get(i).getBookId();
            bookInfo[i][1]=allBook.get(i).getBookName();
            bookInfo[i][2]=allBook.get(i).getLeftNum();
            bookInfo[i][3]=allBook.get(i).getFloor();
            bookInfo[i][4]=false;
        }

        DefaultTableModel model = new DefaultTableModel(bookInfo, name);
        bookTable = new JTable(model) {
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

        borrowButton=new JButton("借阅");
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(num);
                List<Integer> selectedBookId =new ArrayList<Integer>();
                try {
                    for (int i = 0; i < bookTable.getRowCount(); i++) {
//                    System.out.println(bookTable.getValueAt(i,4));
                        if ((boolean) bookTable.getValueAt(i, 4)) {
                            int id = (int) bookTable.getValueAt(i, 0);
                            Controller.getController().someoneWantToBorrowOneBook(id);
                        }
                    }
                    new Thread(() -> {
                        try {
                            Controller.getController().resetBookTable();
//                            bookTable.repaint();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                } catch (SQLException ex) {
                    if(ex.getMessage().equals("no book left")){
                        JOptionPane.showMessageDialog(null, "本书没有剩余！！","错误提示框！", JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "你已经借过这本书了！","错误提示框！", JOptionPane.ERROR_MESSAGE);
                    }

                    ex.printStackTrace();
                }
            }
        });
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(bookTable),BorderLayout.CENTER);
        contentPane.add(borrowButton,BorderLayout.SOUTH);
        setTitle("书籍总表");
        setSize(600,400);
        setVisible(true);
    }

    public void refreshTable(List<book> allBook){
        DefaultTableModel model = (DefaultTableModel) bookTable.getModel();//获取defaulttablemodel
        for(int i=0;i<allBook.size();i++){
            model.setValueAt(allBook.get(i).getLeftNum(),i,2);
            model.setValueAt(false,i,4);
        }
    }

    public static void main(String[] args) {

//        BookTable test =new BookTable();
    }
}
