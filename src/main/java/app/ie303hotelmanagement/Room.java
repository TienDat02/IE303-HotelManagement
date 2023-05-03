package app.ie303hotelmanagement;

public class Room {
    private int sTT;
    private String roomID;
    private String roomType;
    private int floor;
    private String status;

    public Room(int sTT, String roomID, String roomType, int floor, String status) {
        this.sTT = sTT;
        this.roomID = roomID;
        this.roomType = roomType;
        this.floor = floor;
        this.status = status;
    }

    public int getsTT() {
        return sTT;
    }
    public void setsTT(int sTT) {
        this.sTT = sTT;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getFloor() {
        return floor;
    }

    public String getStatus() {
        return status;
    }
}