package com.example.cinema.bl.promotion;

import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPStrategyForm;


/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    ResponseVO addVIPCard(int userId,String name);

    ResponseVO getCardById(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPCardForm vipCardForm);

    ResponseVO getCardByUserId(int userId);

    ResponseVO addVIPStrategy(String name,int reach,int minus,int price);

    ResponseVO getCategoryByCardId(int cardId);

    ResponseVO changeVIPStrategy(String beforeName,String name,int reach,int minus,int price);

    ResponseVO getAllVipCardStrategy();

    ResponseVO deleteCategory(String name);



    }
