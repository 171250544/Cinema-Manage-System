package com.example.cinema.data.Refund;

import java.util.List;
import com.example.cinema.po.RefundSchedule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefundMapper {

    int insertRefundSchedule(RefundSchedule refundSchedule);

    void deleteRefundSchedule(int Id);

    List<RefundSchedule> selectAllSchedules();

    RefundSchedule selectRefundById(int Id);

}
