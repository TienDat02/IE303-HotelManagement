package app.ie303hotelmanagement;

public class RoomDetail {

    private String roomID;
    private String roomType;
    private int roomFloor;

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    public float getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(float roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    private float roomPrice;
    private String roomStatus;

    public RoomDetail(String roomID, String roomType, int floor, float roomPrice, String status) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.roomFloor = floor;
        this.roomPrice = roomPrice;
        this.roomStatus = status;
    }


}
