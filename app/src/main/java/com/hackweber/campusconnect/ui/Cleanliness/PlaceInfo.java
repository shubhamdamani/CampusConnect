package com.hackweber.campusconnect.ui.Cleanliness;

public class PlaceInfo {
    private String placeId,placeLocation,placeDescription,date,placeImg;

    public PlaceInfo() {
    }

    public PlaceInfo(String placeId, String placeLocation, String placeDescription, String date, String placeImg) {
        this.placeId = placeId;
        this.placeLocation = placeLocation;
        this.placeDescription = placeDescription;
        this.date = date;
        this.placeImg = placeImg;
    }

    public String getPlaceImg() {
        return placeImg;
    }

    public void setPlaceImg(String placeImg) {
        this.placeImg = placeImg;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceLocation() {
        return placeLocation;
    }

    public void setPlaceLocation(String placeLocation) {
        this.placeLocation = placeLocation;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
