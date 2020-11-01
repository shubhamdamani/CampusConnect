package com.hackweber.campusconnect.ui.LostAndFound;

public class ItemInfo {
    private String itemId,userId,itemName,itemPlace,itemDate,itemDescription,itemContact,itemUri,itemCategory;

    public ItemInfo() {
    }

    public ItemInfo(String itemId, String userId, String itemName, String itemPlace, String itemDate, String itemDescription, String itemContact, String itemCategory) {
        this.itemId = itemId;
        this.userId = userId;
        this.itemName = itemName;
        this.itemPlace = itemPlace;
        this.itemDate = itemDate;
        this.itemDescription = itemDescription;
        this.itemContact = itemContact;
        this.itemCategory = itemCategory;
    }

    public ItemInfo(String itemId, String userId, String itemName, String itemPlace, String itemDate, String itemDescription, String itemContact, String itemUri, String itemCategory) {
        this.itemId = itemId;
        this.userId = userId;
        this.itemName = itemName;
        this.itemPlace = itemPlace;
        this.itemDate = itemDate;
        this.itemDescription = itemDescription;
        this.itemContact = itemContact;
        this.itemUri = itemUri;
        this.itemCategory = itemCategory;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemUri() {
        return itemUri;
    }

    public void setItemUri(String itemUri) {
        this.itemUri = itemUri;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPlace() {
        return itemPlace;
    }

    public void setItemPlace(String itemPlace) {
        this.itemPlace = itemPlace;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemContact() {
        return itemContact;
    }

    public void setItemContact(String itemContact) {
        this.itemContact = itemContact;
    }
}
