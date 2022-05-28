package helper;

import Frame.*;
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
        Vars.welcomeFrame.setVisible(false);
    }

    public void showSeatTable() throws SQLException {
        if(SeatTable.state==0){
            List<Seat> allSeat =mySqlConnector.getAllSeat();
            Vars.seatTable.setSeatData(allSeat);
            Vars.seatTable.setVisible(true);
            SeatTable.state=1;
        }else {
            Vars.seatTable.setVisible(true);
        }

    }

    public void userLogIn(String userName ,String password) throws SQLException {
        int success=mySqlConnector.userLogIn(userName,password);
        if (success>0){
            Vars.chooseOperation.setVisible(true);
            Vars.logInFrame.setVisible(false);
            myBorrower = mySqlConnector.getBorrowerInfo(userName);
//            System.out.println(success);\
            new Thread(()->{
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(success==2){
                    JOptionPane.showConfirmDialog(null, "欢迎回来,"+myBorrower.name+ "同学", "messageBox", JOptionPane.DEFAULT_OPTION);
                }else if(success==3){
                    JOptionPane.showConfirmDialog(null, "欢迎回来,"+myBorrower.name+ "老师", "messageBox", JOptionPane.DEFAULT_OPTION);
                }
                if(myBorrower instanceof student){
                    System.out.println("同学");
                }
                if(myBorrower instanceof teacher){
                    System.out.println("老师");
                }
            }).start();

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
        if(ReturnFrame.state==0){
            List<BorrowInfo> allBorrowInfo =mySqlConnector.getALLBorrowInfo(myBorrower.name);
            Vars.returnFrame.setBorrowInfoTable(allBorrowInfo);
            Vars.returnFrame.setVisible(true);
            ReturnFrame.state =1;
        }else{
            List<BorrowInfo> allBorrowInfo =mySqlConnector.getALLBorrowInfo(myBorrower.name);
            Vars.returnFrame.resetTable(allBorrowInfo);
            Vars.returnFrame.setVisible(true);
        }

    }

    public void someoneWantToReturnBook(String bookName) throws SQLException {
        mySqlConnector.transcationReturnBook(bookName,myBorrower.id);
    }

    public void showOneSeatInfo(int seatId) throws SQLException {
        List<MyTimePair> timeResevered =mySqlConnector.getReservedTime(seatId);
        boolean[] isAvailebel =new boolean[24];
        for(int i=0;i<24;i++){
            isAvailebel[i]=true;
        }
        for(int i=0;i<timeResevered.size();i++){
            for(int j=timeResevered.get(i).startTime;j<=timeResevered.get(i).endTime;j++){
                isAvailebel[j] = false;
            }
        }
        Vars.seatFrame.setSeatId(seatId);
        Vars.seatFrame.setTimePanel(isAvailebel);
        Vars.seatFrame.setVisible(true);

    }


    public void someoneReserveSeat(int seatId, int start, int end) throws SQLException {
        mySqlConnector.someoneReserveSeat(myBorrower.id,seatId,start,end);
    }

    public void resetSeatFrame(int seatId) throws SQLException {
        List<MyTimePair> timeResevered =mySqlConnector.getReservedTime(seatId);
        boolean[] isAvailebel =new boolean[24];
        for(int i=0;i<24;i++){
            isAvailebel[i]=true;
        }
        for(int i=0;i<timeResevered.size();i++){
            for(int j=timeResevered.get(i).startTime;j<=timeResevered.get(i).endTime;j++){
                isAvailebel[j] = false;
            }
        }
        Vars.seatFrame.setTimePanel(isAvailebel);
    }

    public void showReleaseFrame() throws SQLException {
        if(RealeaseSeatFrame.state==0){
            List<ReserveInfo> allReserveInfo =mySqlConnector.getALLReserveInfo(myBorrower.id);
            Vars.realeaseSeatFrame.setReserveInfoTable(allReserveInfo);
            Vars.realeaseSeatFrame.setVisible(true);
            RealeaseSeatFrame.state=1;
        }else{
            List<ReserveInfo> allReserveInfo =mySqlConnector.getALLReserveInfo(myBorrower.id);
            Vars.realeaseSeatFrame.resetTable(allReserveInfo);
            Vars.realeaseSeatFrame.setVisible(true);
        }
    }

    public void realeaseSeat(int seatId) throws SQLException {
        mySqlConnector.realeaseSeat(myBorrower.id,seatId);
    }

    public void showAdminFrame() throws SQLException {
        if(AdminFrame.state ==0){
            List<book> allBook =mySqlConnector.getAllBook();
            Vars.adminFrame.setTableData(allBook);
            Vars.adminFrame.setVisible(true);
            BookTable.state =1;
        }else {
            List<book> allBook =mySqlConnector.getAllBook();
            Vars.adminFrame.refreshTable(allBook);
            Vars.adminFrame.setVisible(true);
        }
    }

    public void showForDemoFrame() {
        Vars.forDemoFrame.setVisible(true);
    }

    public void borrowBookForDemo(int borrowerId, int bookId) throws SQLException {
        mySqlConnector.someoneBorrow(borrowerId,bookId);
    }
}
