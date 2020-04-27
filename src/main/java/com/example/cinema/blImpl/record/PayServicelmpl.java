package com.example.cinema.blImpl.record;

import com.example.cinema.bl.record.PayService;
import com.example.cinema.blImpl.user.accountServiceForBL;
import com.example.cinema.data.record.PayMapper;
import com.example.cinema.po.PayRecord;
import com.example.cinema.po.User;
import com.example.cinema.po.UserForCoupon;
import com.example.cinema.vo.PayRecordVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.UserForCouponVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PayServicelmpl implements PayService, PayServiceForBL {
    @Autowired
    PayMapper payMapper;
    @Autowired
    accountServiceForBL accountService;

    @Override
    @Transactional
    public ResponseVO getPayByUser(int userId) {
        try {
            List<PayRecord> payRecords = payMapper.selectPayRecordByUser(userId);
//            List<PayRecord> payRecords = selectPayRecordByUser(userId);
            List<PayRecordVO> payRecordVOS = new ArrayList<>();
            for (PayRecord p : payRecords) {
                payRecordVOS.add(p.getVo());
            }
            //System.out.println(getUserByTotal(10.0).toString());
            // System.out.println(accountMapper.getAccountByID(3).getUsername());
            //System.out.println(payRecords.get(0).getTypeOfRecharge());
            //System.out.println(payRecordVOS.get(0).getTypeOfRecharge());
            return ResponseVO.buildSuccess(payRecordVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public void insertPayRecord(PayRecord payRecord) {
        try {
            payMapper.insertPayRecord(payRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    @Transactional
    public ResponseVO getUserByTotal(double total) {
        try {
            List<UserForCoupon> userForCoupons = new ArrayList<>();
            List<PayRecord> payRecords = payMapper.selectUserByTotal();
            for (PayRecord payRecord : payRecords) {
                if (payRecord.getPrice() >= total) {
                    UserForCoupon userForCoupon = new UserForCoupon();
                    userForCoupon.setUserId(payRecord.getUserId());
                    User user = accountService.getAccountByID(payRecord.getUserId());
                    userForCoupon.setUserName(accountService.getAccountByID(payRecord.getUserId()).getUsername());
                    userForCoupon.setHavepayed(payRecord.getPrice());
                    userForCoupons.add(userForCoupon);
                }
            }
            List<UserForCouponVO> userForCouponVOS = new ArrayList<>();
            for (UserForCoupon u : userForCoupons) {
                userForCouponVOS.add(u.geiVO());
            }
            return ResponseVO.buildSuccess(userForCouponVOS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    /**
     * --------------------------------以下为此类中依赖的Mapper方法的桩-----------------------------------------------
     **/
//    List<PayRecord> selectPayRecordByUser(int userId) throws ParseException {
//
//        ArrayList<PayRecord> arrayList = new ArrayList<>();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date1 = format.parse("2019-06-16 12:06:33");//str表示yyyy年MM月dd HH:mm:ss格式字符串
//        Date date2 = format.parse("2019-06-17 12:36:33");//str表示yyyy年MM月dd HH:mm:ss格式字符串
//        arrayList.add(new PayRecord(1, 15, date1, 22, 1, 0));
//        arrayList.add(new PayRecord(2, 15, date2, 33, 1, 0));
//        return arrayList;
//    }
//    List<PayRecord> selectUserByTotal() throws ParseException {
//        ArrayList<PayRecord> arrayList = new ArrayList<>();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date1 = format.parse("2019-06-16 12:06:33");//str表示yyyy年MM月dd HH:mm:ss格式字符串
//        Date date2 = format.parse("2019-06-17 12:36:33");//str表示yyyy年MM月dd HH:mm:ss格式字符串
//        arrayList.add(new PayRecord(1, 15, date1, 22, 1, 0));
//        arrayList.add(new PayRecord(2, 15, date2, 33, 1, 0));
//        return arrayList;
//    }

}
