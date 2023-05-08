package app.ie303hotelmanagement;

public class UsedService {

    private String serviceID;
    private String serviceName;
    private String roomID;
    private float price;
    private int quantity;

    private float total;

    public UsedService( String serviceID, String serviceName, String roomID , float price, int quantity, float total) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.roomID = roomID;
    }





    public String getServiceID() {
        return serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public float getPrice() {
        return price;
    }

    public float getTotal() {
        return total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotal(float total) {
        this.total = total;
    }
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }
    public String getRoomID() {
        return roomID;
    }

}
