package com.hackweber.campusconnect.model;

public class Canteen {

    private String name;
    private String address;
    private String uniqueID;
    private String url;

    public Canteen(String mCanteenName,String mCanteenAddress){
        name = mCanteenName;
        address = mCanteenAddress;

    }
    public Canteen(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
