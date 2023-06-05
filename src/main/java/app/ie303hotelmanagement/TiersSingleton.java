package app.ie303hotelmanagement;

public class TiersSingleton {
    private static TiersSingleton instance = new TiersSingleton();
    private int diamondDiscount;
    private int goldDiscount;
    private int silverDiscount;
    private int diamondNearestCheckouts;
    private int goldNearestCheckouts;
    private int silverNearestCheckouts;
    private double diamondNearestCheckoutValue;
    private double goldNearestCheckoutValue;
    private double silverNearestCheckoutValue;
    public static TiersSingleton getInstance() {
        return instance;
    }
    public void setDiamondDiscount(int diamondDiscount) {
        this.diamondDiscount = diamondDiscount;
    }
    public int getDiamondDiscount() {
        return diamondDiscount;
    }
    public void setGoldDiscount(int goldDiscount) {
        this.goldDiscount = goldDiscount;
    }
    public int getGoldDiscount() {
        return goldDiscount;
    }
    public void setSilverDiscount(int silverDiscount) {
        this.silverDiscount = silverDiscount;
    }
    public int getSilverDiscount() {
        return silverDiscount;
    }
    public void setDiamondNearestCheckouts(int diamondNearestCheckouts) {
        this.diamondNearestCheckouts = diamondNearestCheckouts;
    }
    public int getDiamondNearestCheckouts() {
        return diamondNearestCheckouts;
    }
    public void setGoldNearestCheckouts(int goldNearestCheckouts) {
        this.goldNearestCheckouts = goldNearestCheckouts;
    }
    public int getGoldNearestCheckouts() {
        return goldNearestCheckouts;
    }
    public void setSilverNearestCheckouts(int silverNearestCheckouts) {
        this.silverNearestCheckouts = silverNearestCheckouts;
    }
    public int getSilverNearestCheckouts() {
        return silverNearestCheckouts;
    }
    public void setDiamondNearestCheckoutValue(double diamondNearestCheckoutValue) {
        this.diamondNearestCheckoutValue = diamondNearestCheckoutValue;
    }
    public double getDiamondNearestCheckoutValue() {
        return diamondNearestCheckoutValue;
    }
    public void setGoldNearestCheckoutValue(double goldNearestCheckoutValue) {
        this.goldNearestCheckoutValue = goldNearestCheckoutValue;
    }
    public double getGoldNearestCheckoutValue() {
        return goldNearestCheckoutValue;
    }
    public void setSilverNearestCheckoutValue(double silverNearestCheckoutValue) {
        this.silverNearestCheckoutValue = silverNearestCheckoutValue;
    }
    public double getSilverNearestCheckoutValue() {
        return silverNearestCheckoutValue;
    }
}
