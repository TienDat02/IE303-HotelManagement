package app.ie303hotelmanagement;

public class RoomDisplay {


    private int ordinalNumber;
    private String roomID;
    private String roomType;
    private int roomFloor;
    private String roomStatus;

    public RoomDisplay(int ordinalNumber, String roomID, String roomType, int roomFloor, String roomStatus) {
        this.ordinalNumber = ordinalNumber;
        this.roomID = roomID;
        this.roomType = roomType;
        this.roomFloor = roomFloor;
        this.roomStatus = roomStatus;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }
}
