package allClass;

public class Seat {
    public int seatId;
    public int floor;

    public Seat(int seatId, int floor) {
        this.seatId = seatId;
        this.floor = floor;
    }

    public int getSeatId() {
        return seatId;
    }

    public int getFloor() {
        return floor;
    }
}
