package com.hackweber.campusconnect.ui.Cleanliness;

public class SearchModel {
    private String searchName,placeId;

    public SearchModel() {
    }

    public SearchModel(String searchName, String placeId) {
        this.searchName = searchName;
        this.placeId = placeId;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
