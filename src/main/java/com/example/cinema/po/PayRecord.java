package com.example.cinema.po;

import com.example.cinema.vo.PayRecordVO;
import com.example.cinema.vo.RechargeRecordVO;

import java.util.Date;

public class PayRecord {
    private int id;
    private int userId;
    private Date timestamp;
    private double price;
    private int typeOfRecharge;
    private int moreOfPay;

    public PayRecordVO getVo(){
        PayRecordVO payRecordVO=new PayRecordVO();
        payRecordVO.setPrice(this.price);
        payRecordVO.setRecordId(this.id);
        payRecordVO.setTimeOfRecharge(this.timestamp);
        payRecordVO.setUserId(this.userId);
        String stateString;
        switch (typeOfRecharge) {
            case 0:
                stateString = "银行卡";
                break;
            case 1:
                stateString = "会员卡";
                break;
            default:
                stateString="未知";
        }
        payRecordVO.setTypeOfRecharge(stateString);
        String aString="";
        switch (moreOfPay) {
            case 0:
                aString = "购票";
                break;
            case 1:
                aString = "退票";
                break;
            default:
                aString="未知";
        }
        payRecordVO.setMoreOfPaying(aString);
        return  payRecordVO;
    }

//    public PayRecord(int id, int userId, Date timestamp, double price, int typeOfRecharge, int moreOfPay) {
//        this.id = id;
//        this.userId = userId;
//        this.timestamp = timestamp;
//        this.price = price;
//        this.typeOfRecharge = typeOfRecharge;
//        this.moreOfPay = moreOfPay;
//    }

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

    public Date getTimestamp() {
        return timestamp;
    }

    public double getPrice() {
        return price;
    }

    public int getTypeOfRecharge() {
        return typeOfRecharge;
    }

    public int getMoreOfPay(){return moreOfPay;}

    public void setMoreOfPay(int a){this.moreOfPay=a;}
}
