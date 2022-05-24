package allClass;

public class BorrowInfo {
    String bookName;
    String BorrowName;
    String startTime;
    String endTime;

    public BorrowInfo(String bookName, String borrowName, String startTime, String endTime) {
        this.bookName = bookName;
        BorrowName = borrowName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBorrowName() {
        return BorrowName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
