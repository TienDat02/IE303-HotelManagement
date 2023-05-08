package app.ie303hotelmanagement;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;

public class CheckoutRoomDetails {
    private String roomID;
    private String roomType;
    private float roomPrice;
    private int roomFloor;
    private Date checkinDate;
    private Time checkinTime;
    private Time checkoutTime;
    private Date checkoutDate;
    private float hours;
    private float total;

    public CheckoutRoomDetails(String roomID, String roomType, float roomPrice, int roomFloor, Date checkinDate, Time checkinTime, Date checkoutDate, Time checkoutTime) {
        this.roomID = roomID;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomFloor = roomFloor;
        this.checkinDate = checkinDate;
        this.checkinTime = checkinTime;
        this.checkoutDate = checkoutDate;
        this.checkoutTime = checkoutTime;
        LocalDateTime checkinDateTime = LocalDateTime.of(checkinDate.toLocalDate(), checkinTime.toLocalTime());

        LocalDateTime checkoutDateTime = LocalDateTime.of(checkoutDate.toLocalDate(), checkoutTime.toLocalTime());
        Duration duration = Duration.between(checkinDateTime, checkoutDateTime);
        this.hours = duration.toHours();
        total = (hours/24) * roomPrice;
    }
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
    public void setRoomPrice(float roomPrice) {
        this.roomPrice = roomPrice;
    }
    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }
    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }
    public void setCheckinTime(Time checkinTime) {
        this.checkinTime = checkinTime;
    }
    public void setCheckoutTime(Time checkoutTime) {
        this.checkoutTime = checkoutTime;
    }
    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }
    public void setHours(float hours) {
        this.hours = hours;
    }
    public void setTotal(float total) {
        this.total = total;
    }
    public String getRoomID() {
        return roomID;
    }
    public String getRoomType() {
        return roomType;
    }
    public float getRoomPrice() {
        return roomPrice;
    }
    public int getRoomFloor() {
        return roomFloor;
    }
    public Date getCheckinDate() {
        return checkinDate;
    }
    public Time getCheckinTime() {
        return checkinTime;
    }
    public Time getCheckoutTime() {
        return checkoutTime;
    }
    public Date getCheckoutDate() {
        return checkoutDate;
    }
    public float getHours() {
        return hours;
    }
    public float getTotal() {
        return total;
    }

}
