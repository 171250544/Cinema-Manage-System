package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.VIPCard;

public interface VipServiceForBL {
    public void updateBalance(int id,double balance);
    VIPCard selectCardByUserId(int id);
}
