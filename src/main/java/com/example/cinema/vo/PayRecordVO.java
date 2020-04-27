package com.example.cinema.vo;

import java.util.Date;

public class PayRecordVO {
    private double price;
    private String typeOfPay;
    private int recordId;
    private int userId;
    private Date timeOfRecharge;
    private String moreOfPaying;

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTypeOfRecharge(String typeOfRecharge) {
        this.typeOfPay = typeOfRecharge;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTimeOfRecharge(Date timeOfRecharge) {
        this.timeOfRecharge = timeOfRecharge;
    }

    public double getPrice() {
        return price;
    }

    public String getTypeOfRecharge() {
        return typeOfPay;
    }

    public int getRecordId() {
        return recordId;
    }

    public int getUserId() {
        return userId;
    }

    public Date getTimeOfRecharge() {
        return timeOfRecharge;
    }

    public String getMoreOfPaying(){return moreOfPaying;}

    public void setMoreOfPaying(String a){this.moreOfPaying=a;}

}
