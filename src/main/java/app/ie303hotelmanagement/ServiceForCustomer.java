package app.ie303hotelmanagement;

public class ServiceForCustomer {
    int ordinalNumber;
    String serviceID;
    String serviceName;
    float price;
    String description;

    public ServiceForCustomer(int ordinalNumber, String serviceID, String serviceName, float price, String description) {
        this.ordinalNumber = ordinalNumber;
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.price = price;
        this.description = description;
    }

    public int getOrdinalNumber() {
        return ordinalNumber;
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

    public String getDescription() {
        return description;
    }
    public void setOrdinalNumber(int ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
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

    public void setDescription(String description) {
        this.description = description;
    }

}
