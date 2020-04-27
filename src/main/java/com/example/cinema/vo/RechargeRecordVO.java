package com.example.cinema.vo;

import java.sql.Timestamp;
import java.util.Date;

public class RechargeRecordVO {
    private double price;
    private String typeOfRecharge;
    private int recordId;
    private int userId;
    private java.util.Date timeOfRecharge;

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTypeOfRecharge(String typeOfRecharge) {
        this.typeOfRecharge = typeOfRecharge;
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
        return typeOfRecharge;
    }

    public int getRecordId() {
        return recordId;
    }

    public int getUserId() {
        return userId;
    }

    public java.util.Date getTimeOfRecharge() {
        return timeOfRecharge;
    }
}
