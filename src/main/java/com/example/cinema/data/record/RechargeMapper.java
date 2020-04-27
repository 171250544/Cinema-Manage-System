package com.example.cinema.data.record;

import com.example.cinema.po.RechargeRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RechargeMapper {
    List<RechargeRecord> selectRechargeRecordByUser(int userId);

    void  insertRechargeRecord (RechargeRecord rechargeRecord);

}
