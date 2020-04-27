package com.example.cinema.bl.record;

import com.example.cinema.po.PayRecord;
import com.example.cinema.po.RechargeRecord;
import com.example.cinema.vo.ResponseVO;

public interface PayService {
    /**
     * TODO:获得用户的消费记录
     *
     * @param userId
     * @return  RechargeRecordVo
     */
    public ResponseVO getPayByUser(int userId);





    /**
     * TODO:获得达到条件的用户
     *
     * @param total
     * @return  RechargeRecordVo
     */
    public ResponseVO getUserByTotal(double total);
}
