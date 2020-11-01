package com.hackweber.campusconnect.model;

public class NotificationsItem {
    private String itemId,category,body,title;

    public NotificationsItem(){}

    public NotificationsItem(String itemId, String body, String title,String category) {
        this.itemId = itemId;
        this.body = body;
        this.title = title;
        this.category=category;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
