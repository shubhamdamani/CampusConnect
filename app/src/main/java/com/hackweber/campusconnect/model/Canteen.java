package com.hackweber.campusconnect.model;

public class Canteen {

    private String canteenName;
    private String canteenAddress;

    public Canteen(String mCanteenName,String mCanteenAddress){
        canteenName = mCanteenName;
        canteenAddress = mCanteenAddress;

    }

    public String getCanteenAddress() {
        return canteenAddress;
    }

    public void setCanteenAddress(String canteenAddress) {
        this.canteenAddress = canteenAddress;
    }

    public String getCanteenName() {
        return canteenName;
    }

    public void setCanteenName(String canteenName) {
        this.canteenName = canteenName;
    }
}
