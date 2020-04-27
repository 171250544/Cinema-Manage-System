package com.example.cinema.bl.record;

import com.example.cinema.po.RechargeRecord;
import com.example.cinema.vo.ResponseVO;

public interface RechargeService {
    /**
     * TODO:获得用户的充值记录
     *
     * @param userId
     * @return  RechargeRecordVo
     */
    public ResponseVO getRechargeByUser(int userId);

}
