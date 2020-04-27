package com.example.cinema.vo;

import com.example.cinema.po.User;

public class StaffVO {

    private int id;
    private String username;
    private String role;

    public StaffVO(){}

    public StaffVO(User user){
        this.id = user.getId();
        this.role = user.getRole();
        this.username = user.getUsername();
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
