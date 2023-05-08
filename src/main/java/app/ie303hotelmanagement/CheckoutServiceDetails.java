package app.ie303hotelmanagement;

import java.sql.Time;
import java.sql.Date;

public class CheckoutServiceDetails {
    private Date serviceDate;
    private Time ServiceTime;
    private String serviceRoomID;
    private String serviceID;
    private String serviceName;
    private float servicePrice;
    private int serviceQuantity;
    private float serviceTotal;

    public CheckoutServiceDetails(String serviceID, String serviceName, String serviceRoomID, int serviceQuantity, float servicePrice,  Date serviceDate, Time serviceTime) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceQuantity = serviceQuantity;
        this.serviceDate = serviceDate;
        this.ServiceTime = serviceTime;
        this.serviceRoomID = serviceRoomID;
        this.serviceTotal = servicePrice * serviceQuantity;
    }

    public float getServiceTotal() {
        return serviceTotal;
    }
    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServicePrice(float servicePrice) {
        this.servicePrice = servicePrice;
    }

    public void setServiceQuantity(int serviceQuantity) {
        this.serviceQuantity = serviceQuantity;
    }

    public void setTotal(float total) {
        this.serviceTotal = total;
    }

    public String getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public float getServicePrice() {
        return servicePrice;
    }

    public int getServiceQuantity() {
        return serviceQuantity;
    }
    public Date getServiceDate() {
        return serviceDate;
    }
    public Time getServiceTime() {
        return ServiceTime;
    }
    public String getServiceRoomID() {
        return serviceRoomID;
    }

}
