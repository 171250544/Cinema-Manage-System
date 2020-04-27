package com.example.cinema.blImpl.record;

import com.example.cinema.bl.record.RechargeService;
import com.example.cinema.data.record.RechargeMapper;
import com.example.cinema.po.RechargeRecord;
import com.example.cinema.vo.RechargeRecordVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RechargeServicelmpl implements RechargeService, RechargeServiceForBL {
    @Autowired
    RechargeMapper rechargeMapper;

    @Override
    @Transactional
    public ResponseVO getRechargeByUser(int userId){
        try {
            List<RechargeRecord> rechargeRecords = rechargeMapper.selectRechargeRecordByUser(userId);
//            List<RechargeRecord> rechargeRecords = selectRechargeRecordByUser(userId);
            List<RechargeRecordVO> rechargeRecordVOS = new ArrayList<>();
            for (RechargeRecord t : rechargeRecords) {
                rechargeRecordVOS.add(t.getVo());
            }
            return ResponseVO.buildSuccess(rechargeRecordVOS);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public void insertRechargeRecord(RechargeRecord rechargerecord) {
        try {
            rechargeMapper.insertRechargeRecord(rechargerecord);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**--------------------------------以下为此类中依赖的Mapper方法的桩-----------------------------------------------**/

//    List<RechargeRecord> selectRechargeRecordByUser(int userId) throws ParseException {
//        ArrayList<RechargeRecord> arrayList = new ArrayList<>();
//        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date1=format.parse("2019-06-16 12:06:33");//str表示yyyy年MM月dd HH:mm:ss格式字符串
//        Date date2=format.parse("2019-06-17 12:36:33");//str表示yyyy年MM月dd HH:mm:ss格式字符串
//
//        arrayList.add(new RechargeRecord(5, 15, date1, 30,0));
//        arrayList.add(new RechargeRecord(6, 15, date2, 200,0));
//        return arrayList;
//    };

}
