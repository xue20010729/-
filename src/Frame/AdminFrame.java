package Frame;

import allClass.book;
import com.sun.org.apache.xpath.internal.objects.XObject;
import helper.Controller;
import org.w3c.dom.html.HTMLButtonElement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminFrame extends JFrame{

    class InputFrame extends JFrame{
        JTextField newIdField;
        JButton alterButton;
        int oldId;
        InputFrame(){
            newIdField=new JTextField();
            alterButton=new JButton("修改");
            alterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String str=newIdField.getText();
                    int newId=Integer.parseInt(str);
                    System.out.println(newId);
                }
            });
            Container contentPane=getContentPane();
            contentPane.setLayout(new GridLayout(2,1));
            contentPane.add(newIdField);
            contentPane.add(alterButton);
            setTitle("更改表");
            setSize(400,300);
        }
        public void setOldId(int oldId){
            this.oldId=oldId;
        }
    }
    private JTable bookTable;
    private JButton borrowButton;
    InputFrame inputFrame;
    public static int state = 0;
    AdminFrame(){

    }

    public void setTableData(List<book> allBook){
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inputFrame=new InputFrame();
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

        borrowButton=new JButton("修改");
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(num);
                List<Integer> selectedBookId =new ArrayList<Integer>();
                for (int i = 0; i < bookTable.getRowCount(); i++) {
//                    System.out.println(bookTable.getValueAt(i,4));
                    if ((boolean) bookTable.getValueAt(i, 4)) {
                        int id = (int) bookTable.getValueAt(i, 0);
                        selectedBookId.add(id);
                    }
                }
                if(selectedBookId.size()!=1) {
                    JOptionPane.showConfirmDialog(null, "选择的数量不符合规则", "ConfirmDialog", JOptionPane.DEFAULT_OPTION);
                }else {
                    inputFrame.setOldId(selectedBookId.get(0));
                    inputFrame.setVisible(true);
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

