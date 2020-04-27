package com.example.cinema.blImpl.record;

import com.example.cinema.po.RechargeRecord;
import com.example.cinema.vo.ResponseVO;

public interface RechargeServiceForBL {


    /**
     * TODO:增加一条充值记录
     *
     * @param rechargerecord
     * @return
     */
    public void insertRechargeRecord(RechargeRecord rechargerecord);
}
