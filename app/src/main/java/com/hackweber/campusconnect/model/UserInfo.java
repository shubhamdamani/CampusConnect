package com.hackweber.campusconnect.model;

public class UserInfo {
    private String userId,userEmail,userName,userPhone;

    public UserInfo() {
    }

    public UserInfo(String userId, String userEmail, String userName, String userPhone) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPhone = userPhone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}
