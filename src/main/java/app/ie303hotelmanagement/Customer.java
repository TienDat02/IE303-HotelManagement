package app.ie303hotelmanagement;

public class Customer {
    long stt;
    private String cccd;
    private String customerName;
    private int numberOfCheckin;
    private String tier;
    private int birthYear;
    private String phoneNumber;
    private String note;
    Customer(long stt, String cccd, String customerName, int numberOfCheckin, String tier, int birthYear, String phoneNumber, String note){
        this.stt = stt;
        this.customerName = customerName;
        this.cccd = cccd;
        this.birthYear = birthYear;
        this.phoneNumber = phoneNumber;
        this.note = note;
        this.numberOfCheckin = numberOfCheckin;
        this.tier = tier;
    }
    public int getBirthYear() {
        return birthYear;
    }
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
    public long getSTT() {
        return stt;
    }
    public void setSTT(long stt) {
        this.stt = stt;
    }
    public String getName() {
        return customerName;
    }
    public void setName(String name) {
        customerName = name;
    }
    public String getCccd() {
        return cccd;
    }
    public void setCccd(String cccd) {
        this.cccd = cccd;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public int getNumberOfCheckin() {
        return numberOfCheckin;
    }
    public void setNumberOfCheckin(int numberOfCheckin) {
        this.numberOfCheckin = numberOfCheckin;
    }
    public String getTier() {
        return tier;
    }
    public void setTier(String tier) {
        this.tier = tier;
    }


}