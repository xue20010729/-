package allClass;

public class ReserveInfo {
    int borrowerId;
    int seatId;
    int start;
    int end;
    String date;

    public ReserveInfo(int borrowerId, int seatId, int start, int end, String date) {
        this.borrowerId = borrowerId;
        this.seatId = seatId;
        this.start = start;
        this.end = end;
        this.date = date;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public int getSeatId() {
        return seatId;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getDate() {
        return date;
    }
}
