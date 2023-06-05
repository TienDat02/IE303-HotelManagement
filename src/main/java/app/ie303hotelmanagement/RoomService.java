package app.ie303hotelmanagement;

import java.sql.Time;
import java.util.Date;

public class RoomService {
    private int roomServiceID;
    private int serviceID;
    private String serviceName;
    private int roomID;
    private Date roomServiceDate;
    private Time roomServiceTime;
    private int quantity;
    private String note;
    RoomService(int roomServiceID, int serviceID, String serviceName,  int roomID, Date roomServiceDate, Time roomServiceTime, int quantity, String note){
        this.roomServiceID = roomServiceID;
        this.serviceID = serviceID;
        this.roomID = roomID;
        this.roomServiceDate = roomServiceDate;
        this.roomServiceTime = roomServiceTime;
        this.quantity = quantity;
        this.serviceName = serviceName;
        this.note = note;
    }
    public int getRoomServiceID() {
        return roomServiceID;
    }
    public int getServiceID() {
        return serviceID;
    }
    public int getRoomID() {
        return roomID;
    }
    public Date getRoomServiceDate() {
        return roomServiceDate;
    }
    public Time getRoomServiceTime() {
        return roomServiceTime;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setRoomServiceID(int roomServiceID) {
        this.roomServiceID = roomServiceID;
    }
    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }
    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }
    public void setRoomServiceDate(Date roomServiceDate) {
        this.roomServiceDate = roomServiceDate;
    }
    public void setRoomServiceTime(Time roomServiceTime) {
        this.roomServiceTime = roomServiceTime;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getServiceName() {
        return serviceName;
    }
    public String getNote() {
        return note;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
