package com.example.cinema.po;

public class VipCardCategory {
    private String name;
    private int reach;
    private int minus;
    private int price;

    public VipCardCategory(String name, int reach, int minus, int price) {
        this.name = name;
        this.reach = reach;
        this.minus = minus;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }

    public int getMinus() {
        return minus;
    }

    public void setMinus(int minus) {
        this.minus = minus;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String makeDescription(){
        String result="";
        result="满"+Integer.toString(reach)+"送"+Integer.toString(minus);
        return result;
    }
    public double calculate(double amount){
        return (int) (amount / reach) * minus + amount;
    }
}
