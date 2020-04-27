package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VipCardCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VIPCardMapper {

    int insertOneCard(VIPCard vipCard);

    VIPCard selectCardById(int id);

    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

    VIPCard selectCardByUserId(int userId);

    void insertOneStrategy(VipCardCategory vipCardCategory);

    VipCardCategory selectCategoryByName(String name);

    VipCardCategory selectCategoryByCardId(int cardId);

    List<VipCardCategory> selectAllCategory();

    void deleteCardStrategy(@Param("nameOfCategory") String nameOfCategory);
    void deleteVipCard(@Param("nameOfCard") String nameOfCard);
    void updateStrategyOfVipCard(@Param("beforeName")String beforeName,@Param("name")String name);
}
