package com.hackweber.campusconnect.model;

public class Order {
    public String orderItems;
    public String amount;
    public String orderedOn;

    public Order(){}
    public Order(String orderItems,String amount,String orderedOn){
        this.orderItems=orderItems;
        this.amount=amount;
        this.orderedOn=orderedOn;
    }
}
