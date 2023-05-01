package app.ie303hotelmanagement;

import java.sql.Date;
import java.sql.Time;

public class CheckOut {
    private String guestID;
    private Time checkOutTime;

    private Date checkOutDate;

    private int totalTime;

    private float totalCost;

    private String roomID;

    private String employeeID;


    public CheckOut(String guestID, Time checkOutTime, Date checkOutDate, int totalTime, float totalCost, String roomID, String employeeID) {
        this.guestID = guestID;
        this.checkOutTime = checkOutTime;
        this.checkOutDate = checkOutDate;
        this.totalTime = totalTime;
        this.totalCost = totalCost;
        this.roomID = roomID;
        this.employeeID = employeeID;
    }

    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    public Time getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Time checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
}
