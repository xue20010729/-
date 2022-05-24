package helper;

import Frame.BookTable;
import Frame.Vars;
import allClass.*;


import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class Controller {
    private MySqlConnector mySqlConnector;
    private static Controller instance;
    private borrower myBorrower;
    private Controller(){
        mySqlConnector = new MySqlConnector();
    }
    public static Controller getController(){
        if(instance==null){
            instance =new Controller();
        }
        return instance;
    }

    public void showBookTable() throws SQLException {
        if(BookTable.state ==0){
            List<book> allBook =mySqlConnector.getAllBook();
            Vars.bookTable.setTableData(allBook);
            Vars.bookTable.setVisible(true);
            BookTable.state =1;
        }else {
            resetBookTable();
            Vars.bookTable.setVisible(true);
        }

    }

//    public void showChooseOperationFrame(){
//        Vars.chooseOperation.setVisible(true);
//    }

    public void showLogInFrame(){
        Vars.logInFrame.setVisible(true);
    }

    public void showSeatTable() throws SQLException {
        List<Seat> allSeat =mySqlConnector.getAllSeat();
        Vars.seatTable.setSeatData(allSeat);
        Vars.seatTable.setVisible(true);
    }

    public void userLogIn(String userName ,String password) throws SQLException {
        boolean success=mySqlConnector.userLogIn(userName,password);
        if (success){
            Vars.chooseOperation.setVisible(true);
            Vars.logInFrame.setVisible(false);
            myBorrower = mySqlConnector.getBorrowerInfo(userName);
        }else{
            JOptionPane.showConfirmDialog(null, "账户不存在", "ConfirmDialog", JOptionPane.DEFAULT_OPTION);
        }
    }
    public static void main(String[] args) {
//        Controller controller =new Controller();
        Vars.welcomeFrame.setVisible(true);
    }

    public void someoneWantToBorrowOneBook(int id) throws SQLException {
        mySqlConnector.someoneBorrow(myBorrower.id,id);
    }

    public void resetBookTable() throws SQLException {
        List<book> allBook =mySqlConnector.getAllBook();
        Vars.bookTable.refreshTable(allBook);
    }

    public void showReturnFrame() throws SQLException {
        List<BorrowInfo> allBorrowInfo =mySqlConnector.getALLBorrowInfo(myBorrower.name);
        Vars.returnFrame.setBorrowInfoTable(allBorrowInfo);
        Vars.returnFrame.setVisible(true);
    }

    public void someoneWantToReturnBook(String bookName) throws SQLException {
        mySqlConnector.transcationReturnBook(bookName,myBorrower.id);
    }
}
