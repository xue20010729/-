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
                for (int i=0;i<bookTable.getRowCount();i++){
//                    System.out.println(bookTable.getValueAt(i,4));
                    if((boolean)bookTable.getValueAt(i,4)){
                        int id=(int) bookTable.getValueAt(i,0);
                        try {
                            Controller.getController().someoneWantToBorrowOneBook(id);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new JScrollPane(bookTable),BorderLayout.CENTER);
        contentPane.add(borrowButton,BorderLayout.SOUTH);
        setTitle("书籍总表");
        setSize(600,200);
        setVisible(true);
    }

    public static void main(String[] args) {

//        BookTable test =new BookTable();
    }
}
