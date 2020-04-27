package com.example.cinema.vo;

import com.example.cinema.po.ScheduleItem;
import com.example.cinema.po.Ticket;

import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/16.
 */
public class TicketWithScheduleVO {

    /**
     * 电影票id
     */
    private int id;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 排片id
     */
    private ScheduleItem schedule;
    /**
     * 列号
     */
    private int columnIndex;
    /**
     * 排号
     */
    private int rowIndex;

    /**
     * 订单状态
     */
    private String state;

    private Timestamp time;

    private double price;

    public TicketWithScheduleVO() {
    }

    public TicketWithScheduleVO(Ticket ticket, ScheduleItem scheduleItem){
        setRowIndex(ticket.getRowIndex());
        setColumnIndex(ticket.getColumnIndex());
        setState(ticket.getState() + "");
        setPrice(ticket.getPrice());
        setSchedule(scheduleItem);
        setId(ticket.getId());
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ScheduleItem getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleItem schedule) {
        this.schedule = schedule;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPrice(double price){this.price=price;}

    public double getPrice(){return price;}
}
