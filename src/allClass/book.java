package allClass;

public class book {
    public int bookId;
    public String bookName;
    public int leftNum;
    public int floor;

    public book(int bookId, String bookName, int leftNum, int floor) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.leftNum = leftNum;
        this.floor = floor;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public int getLeftNum() {
        return leftNum;
    }

    public int getFloor() {
        return floor;
    }
}
