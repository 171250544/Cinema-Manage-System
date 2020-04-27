package com.example.cinema.po;

import com.example.cinema.vo.UserForCouponVO;

public class UserForCoupon {
    private int userId;
    private String userName;
    private double havepayed;

    public UserForCouponVO geiVO(){
        UserForCouponVO userForCouponVO=new UserForCouponVO();
        userForCouponVO.setUserName(this.userName);
        userForCouponVO.setHavepayed(this.havepayed);
        userForCouponVO.setUserId(this.userId);
        return userForCouponVO;
    }
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
