package com.example.cinema.po;

import com.example.cinema.vo.RechargeRecordVO;

import java.sql.Timestamp;
import java.util.Date;

public class RechargeRecord {
    private int id;
    private int userId;
    private java.util.Date timestamp;
    private double price;
    private int typeOfRecharge;

    public RechargeRecordVO getVo(){
        RechargeRecordVO rechargeRecordVO=new RechargeRecordVO();
        rechargeRecordVO.setPrice(this.price);
        rechargeRecordVO.setRecordId(this.id);
        rechargeRecordVO.setTimeOfRecharge(this.timestamp);
        rechargeRecordVO.setUserId(this.userId);
        String stateString;
        switch (typeOfRecharge) {
            case 0:
                stateString = "银行卡";
                break;
            default:
                stateString = "未知";
        }
        rechargeRecordVO.setTypeOfRecharge(stateString);
        return  rechargeRecordVO;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTypeOfRecharge(int typeOfRecharge) {
        this.typeOfRecharge = typeOfRecharge;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public java.util.Date getTimestamp() {
        return timestamp;
    }

    public double getPrice() {
        return price;
    }

    public int getTypeOfRecharge() {
        return typeOfRecharge;
    }
}
