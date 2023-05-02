package app.ie303hotelmanagement;
public class Service {
    private String serviceID;
    private String serviceName;
    private float servicePrice;
    private String serviceDescription;
    private String serviceStatus;
    public Service(){
        this.serviceName = "";
        this.servicePrice = 0;
        this.serviceDescription = "";
        this.serviceStatus = "";
    }
    public Service(String serviceID, String serviceName, int servicePrice, String serviceDescription, String serviceStatus){
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.serviceDescription = serviceDescription;
        this.serviceStatus = serviceStatus;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public float getServicePrice() {
        return servicePrice;
    }
    public void setServicePrice(int servicePrice) {
        this.servicePrice = servicePrice;
    }
    public String getServiceDescription() {
        return serviceDescription;
    }
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
    public void setService(Service service){
        this.serviceName = service.getServiceName();
        this.servicePrice = service.getServicePrice();
        this.serviceDescription = service.getServiceDescription();
    }
    public String getServiceID() {
        return serviceID;
    }
    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
}
