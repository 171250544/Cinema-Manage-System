package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.blImpl.record.RechargeServiceForBL;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.po.RechargeRecord;
import com.example.cinema.po.VipCardCategory;
import com.example.cinema.vo.*;
import com.example.cinema.po.VIPCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService ,VipServiceForBL{
    @Autowired
    VIPCardMapper vipCardMapper;

    @Autowired
    RechargeServiceForBL rechargeServiceForBL;

    @Override
    public ResponseVO addVIPCard(int userId, String name) {//新增name参数用于判断会员卡类别
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);
        vipCard.setName(name);
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
//            return ResponseVO.buildSuccess(selectCardById(id));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
//            return ResponseVO.buildSuccess(selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
        List<VIPInfoVO> vipInfoList = new ArrayList<>();
        List<VipCardCategory> vCCList = vipCardMapper.selectAllCategory();//获取所有会员卡种类
//        List<VipCardCategory> vCCList = selectAllCategory();//获取所有会员卡种类
        for (int i = 0; i < vCCList.size(); i++) {//用于将PO转化为VO
            VIPInfoVO vipInfoVO = new VIPInfoVO();
            VipCardCategory vCC = vCCList.get(i);
            vipInfoVO.setDescription(vCC.makeDescription());
            vipInfoVO.setPrice(vCC.getPrice());
            vipInfoVO.setName(vCC.getName());
            vipInfoList.add(vipInfoVO);
        }
        return ResponseVO.buildSuccess(vipInfoList);
    }


    @Override
    public ResponseVO charge(VIPCardForm vipCardForm) {
        int amount = vipCardForm.getAmount();
        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        VipCardCategory vCC = vipCardMapper.selectCategoryByName(vipCard.getName());
//        VIPCard vipCard = selectCardById(vipCardForm.getVipId());
//        VipCardCategory vCC = selectCategoryByName(vipCard.getName());

        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        double balance = vCC.calculate(amount);
        vipCard.setBalance(vipCard.getBalance() + balance);
        try {
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
            RechargeRecord rechargeRecord = new RechargeRecord();
            rechargeRecord.setPrice(balance);
            rechargeRecord.setUserId(vipCardMapper.selectCardById(vipCardForm.getVipId()).getUserId());
//            rechargeRecord.setUserId(selectCardById(vipCardForm.getVipId()).getUserId());
            rechargeRecord.setTypeOfRecharge(0);
            rechargeRecord.setTimestamp(new Date());
            rechargeServiceForBL.insertRechargeRecord(rechargeRecord);
            return ResponseVO.buildSuccess(vipCard);//不知道为什么助教这里返回的是PO
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
//            VIPCard vipCard = selectCardByUserId(userId);

            if (vipCard == null) {
                return ResponseVO.buildFailure("用户卡不存在");
            }
            VipCardVO vipCardVO = new VipCardVO();
            vipCardVO.setId(vipCard.getId());
            vipCardVO.setUserId(vipCard.getUserId());
            vipCardVO.setCardName(vipCard.getName());
            vipCardVO.setBalance(vipCard.getBalance());
            vipCardVO.setJoinDate(vipCard.getJoinDate());
            vipCardVO.setDescription(vipCardMapper.selectCategoryByName(vipCard.getName()).makeDescription());
//            vipCardVO.setDescription(selectCategoryByName(vipCard.getName()).makeDescription());


            return ResponseVO.buildSuccess(vipCardVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addVIPStrategy(String name, int reach, int minus, int price) {
        try {
            VipCardCategory vCC = new VipCardCategory(name,reach,minus,price);
            vipCardMapper.insertOneStrategy(vCC);
            return ResponseVO.buildSuccess(vipCardMapper.selectCategoryByName(name));
//            return ResponseVO.buildSuccess(selectCategoryByName(name));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO changeVIPStrategy(String beforeName, String name, int reach, int minus, int price) {
        try {
            vipCardMapper.updateStrategyOfVipCard(beforeName, name);
            deleteCategory(beforeName);
            addVIPStrategy(name, reach, minus, price);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCategoryByCardId(int cardId) {
        try {
            VipCardCategory vCC = vipCardMapper.selectCategoryByCardId(cardId);
//            VipCardCategory vCC = selectCategoryByCardId(cardId);

            if (vCC == null) {
                return ResponseVO.buildFailure("该卡不存在");
            }
            return ResponseVO.buildSuccess(vCC);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    public ResponseVO getAllVipCardStrategy() {
        try {
            List<VipCardCategory> vCCList = vipCardMapper.selectAllCategory();
//            List<VipCardCategory> vCCList = selectAllCategory();

            return ResponseVO.buildSuccess(vCCList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteCategory(String name) {
        String nameOfCard = name;
        String nameOfCategory = name;
        try {
            vipCardMapper.deleteCardStrategy(nameOfCategory);
            vipCardMapper.deleteVipCard(nameOfCard);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }



    @Override
    public void updateBalance(int id, double balance) {
        vipCardMapper.updateCardBalance(id,balance);
    }

    @Override
    public VIPCard selectCardByUserId(int id) {
        return vipCardMapper.selectCardByUserId(id);
    }
    /**--------------------------------以下为此类中依赖的Mapper方法的桩-----------------------------------------------**/
//    int selectCardById(int id){return 3;}
//    VIPCard selectCardByUserId(int userId);
//    VipCardCategory selectCategoryByName(String name){return new VipCardCategory("钻石卡", 100, 10, 10);};
//    VipCardCategory selectCategoryByCardId(int cardId){return new VipCardCategory("钻石卡", 100, 10, 10);};
//    List<VipCardCategory> selectAllCategory() {//
//        VipCardCategory vcc1 = new VipCardCategory("钻石卡", 100, 10, 10);
//        VipCardCategory vcc2 = new VipCardCategory("金卡", 200, 20, 20);
//        ArrayList<VipCardCategory> arrayList = new ArrayList<>();
//        arrayList.add(vcc1);
//        arrayList.add(vcc2);
//        return arrayList;
//    }
}
