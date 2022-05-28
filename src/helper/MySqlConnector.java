package helper;
import allClass.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MySqlConnector {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost:3306/database_finalwork?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String DB_URL = "jdbc:mysql://192.168.11.132:3306/database_final?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static final String USER = "root";
    static final String PASS = "223502xuechen";

    Connection conn = null;
    Statement stmt = null;
    CallableStatement prepareCall = null;
    PreparedStatement preparedStatement =null;
    //构造函数，先行连接数据库
    public MySqlConnector(){
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    }

    public List<book> getAllBook() throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        String sql ="select * from book";
        ResultSet res = stmt.executeQuery(sql);
        List<book> books = new LinkedList<book>();
        while(res.next()){
                // 通过字段检索
                int id  = res.getInt("book_id");
                String name = res.getString("book_name");
//                String author = res.getString("author");
                int num = res.getInt("num");
                int floor = res.getInt("floor_id");
                books.add(new book(id,name,num,floor));
        }
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return books;
    }

    public List<Seat> getAllSeat() throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        String sql ="select * from seat";
        ResultSet res = stmt.executeQuery(sql);
        List<Seat> seats = new LinkedList<Seat>();
        while(res.next()){
            // 通过字段检索
            int id  = res.getInt("seat_id");
            int floor = res.getInt("floor");
            seats.add(new Seat(id,floor));
        }
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return seats;
    }

    public void someoneBorrow(int borrowerId,int bookId) throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        prepareCall =conn.prepareCall("{call borrow_book(?,?)}");
        prepareCall.setInt(1,borrowerId);
        prepareCall.setInt(2,bookId);
        prepareCall.execute();
        try{
            if(prepareCall!=null) prepareCall.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }

    }

    public int userLogIn(String userName ,String password) throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        // 查找是否有这个人
        String sql ="select count(*) from borrower where name =";
        sql = sql+"'"+userName+"'" +"and password =";
        sql=sql+"'"+password+"'";
//        System.out.println(sql);
        ResultSet res = stmt.executeQuery(sql);
        int num =1;
        while(res.next()){
            num= res.getInt(1);
        }

        int id =0;
        if(num==1){
            String sqlToId="select id from borrower where name =";
            sqlToId=sqlToId+"'"+userName+"'";
            res = stmt.executeQuery(sqlToId);
            while(res.next()){
                id= res.getInt(1);
            }
            String sql1 ="select count(*) from student where id="+id;
            String sql2 ="select count(*) from teacher where id="+id;
            res = stmt.executeQuery(sql1);
            while(res.next()){
                if( res.getInt(1)==1){
                    num =2;
                }
//                System.out.println(res.getInt(1));
            }
            res = stmt.executeQuery(sql2);
            while(res.next()){
                if( res.getInt(1)==1){
                    num =3;
                }
//                System.out.println(res.getInt(1));
            }

        }
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return num ;
    }
    public static void main(String[] args) {
//        try {
//            MySqlConnector connector = new MySqlConnector();
//            connector.someoneBorrow(10,2);
//        }
//        catch (SQLException e){
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
    }

    public borrower getBorrowerInfo(String userName) throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();

        String sql ="select id,score from borrower where name=";
        sql = sql+"'"+userName+"'" ;
//        System.out.println(sql);
        ResultSet res = stmt.executeQuery(sql);

        int id =0;
        int score=100;
        while(res.next()){
            id= res.getInt("id");
            score =res.getInt("score");
        }

        int number=0;
        String str;

        String sql1="select student_number,college from student where id="+id;
        String sql2="select job_number,position from teacher where id="+id;
        res = stmt.executeQuery(sql1);
        while(res.next()){
            number= res.getInt(1);
            str =res.getString(2);
            return new student(id,score,userName,number,str);
        }

        res = stmt.executeQuery(sql2);
        while(res.next()){
            number= res.getInt(1);
            str =res.getString(2);
            return new teacher(id,score,userName,number,str);
        }
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return new borrower(id,score,userName);
    }

    public List<BorrowInfo> getALLBorrowInfo(String name) throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        String sql ="select * from check_borrow_info where borrower_name=";
        sql =sql+"'"+name+"'";
        System.out.println(sql);
        ResultSet res = stmt.executeQuery(sql);
        List<BorrowInfo> borrowInfos = new LinkedList<BorrowInfo>();
        while(res.next()){
            // 通过字段检索
            String bookName =res.getString("book_name");
            String borrowerName = res.getString("borrower_name");
            String startTime= res.getString("start_time");
            String endTime = res.getString("end_time");
            borrowInfos.add(new BorrowInfo(bookName,borrowerName,startTime,endTime));
        }
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return borrowInfos;
    }

    public void transcationReturnBook(String bookName,int borrowerId) throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        // 先找到
        String sql ="select book_id from book where book_name = ";
        sql =sql +"'"+bookName+"'";
        ResultSet res = stmt.executeQuery(sql);
        int bookId=0;
        while(res.next()){
            // 通过字段检索
            bookId  = res.getInt("book_id");
        }


        conn.setAutoCommit(false);//关闭自动提交开启事务
        String sql1="delete from borrow_history where book_id = ";
        sql1 = sql1+bookId+" and borrower_id =" +borrowerId;
        String sql2="update book set num=num+1 where book_id =" + bookId;
        preparedStatement = conn.prepareStatement(sql1);
        preparedStatement.executeUpdate();
        preparedStatement = conn.prepareStatement(sql2);
        preparedStatement.executeUpdate();
        conn.commit();

        conn.setAutoCommit(true);
//        System.out.println(sql1);
//        System.out.println(sql2);
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(preparedStatement!=null) preparedStatement.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }

    }

    public List<MyTimePair> getReservedTime(int seatId) throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        String sql ="select start_time,end_time from reserve_history where seat_id =" +seatId;
        ResultSet res = stmt.executeQuery(sql);
        List<MyTimePair> timeReseverd = new LinkedList<MyTimePair>();
        while(res.next()){
            // 通过字段检索
            int startTime  = res.getInt("start_time");
            int endTime = res.getInt("end_time");
            timeReseverd.add(new MyTimePair(startTime,endTime));
        }
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return timeReseverd;
    }

    public void someoneReserveSeat(int borrowerId, int seatId, int start, int end) throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        prepareCall =conn.prepareCall("{call reserve_seat(?,?,?,?)}");
        prepareCall.setInt(1,borrowerId);
        prepareCall.setInt(2,seatId);
        prepareCall.setInt(3,start);
        prepareCall.setInt(4,end);
        prepareCall.execute();
        try{
            if(prepareCall!=null) prepareCall.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    public List<ReserveInfo> getALLReserveInfo(int BorrowerId) throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        String sql ="select * from reserve_history where borrower_id=";
        sql =sql+BorrowerId;
        System.out.println(sql);
        ResultSet res = stmt.executeQuery(sql);
        List<ReserveInfo> reserveInfos = new LinkedList<ReserveInfo>();
        while(res.next()){
            // 通过字段检索
            int borrowerId =res.getInt(1);
            int seatId = res.getInt(2);
            int start = res.getInt(3);
            int end = res.getInt(4);
            String date = res.getString(5);
            reserveInfos.add(new ReserveInfo(borrowerId,seatId,start,end,date));
        }
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
        return reserveInfos;
    }

    public void realeaseSeat(int borrowerId, int seatId) throws SQLException {
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        conn.setAutoCommit(false);//关闭自动提交开启事务
        String sql1="delete from reserve_history where borrower_id= ";
        sql1 = sql1+borrowerId+" and seat_id=" +seatId;
        preparedStatement = conn.prepareStatement(sql1);
        preparedStatement.executeUpdate();
        conn.commit();

        conn.setAutoCommit(true);
//        System.out.println(sql1);
//        System.out.println(sql2);
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(preparedStatement!=null) preparedStatement.close();
        }catch(SQLException se2){
        }// 什么都不做
        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }
}
