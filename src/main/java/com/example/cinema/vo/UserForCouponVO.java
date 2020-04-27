package com.example.cinema.vo;

public class UserForCouponVO {
    private int userId;
    private String userName;
    private double havepayed;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setHavepayed(double havepayed) {
        this.havepayed = havepayed;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public double getHavepayed() {
        return havepayed;
    }
}
