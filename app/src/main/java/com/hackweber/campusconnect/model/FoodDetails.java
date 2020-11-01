package com.hackweber.campusconnect.model;

public class FoodDetails {

    private String id;
    private String price;
    private String name;
    private String url;
    private int foodQuantity;

    public FoodDetails(){}
    public FoodDetails(String id,String mPrice, String mFoodName, String mFoodImage){
        this.id = id;
        price = mPrice;
        name = mFoodName;
        url = mFoodImage;
        foodQuantity = 0;
    }

    public String getPrice() {
        return price;
    }
    public String getId() {return id;}
    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getFoodQuantity() {
        return foodQuantity;
    }

    public void setFoodQuantity(int i){
        foodQuantity = i;
    }

}
