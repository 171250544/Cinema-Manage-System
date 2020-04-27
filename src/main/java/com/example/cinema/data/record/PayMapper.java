package com.example.cinema.data.record;

import com.example.cinema.po.PayRecord;
import com.example.cinema.po.RechargeRecord;
import com.example.cinema.po.UserForCoupon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PayMapper {
    List<PayRecord> selectPayRecordByUser(int userId);

    void  insertPayRecord(PayRecord payRecord);

    List<PayRecord> selectUserByTotal();
}
