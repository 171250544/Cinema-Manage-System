package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.data.promotion.CouponMapper;
import com.example.cinema.po.Coupon;
import com.example.cinema.vo.CouponForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liying on 2019/4/17.
 */
@Service
public class CouponServiceImpl implements CouponService ,couponServiceForBL{

    @Autowired
    CouponMapper couponMapper;

    @Override
    public ResponseVO getCouponsByUser(int userId) {
        try {
            return ResponseVO.buildSuccess(couponMapper.selectCouponByUser(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addCoupon(CouponForm couponForm) {
        try {
            Coupon coupon=new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());
            couponMapper.insertCoupon(coupon);
            return ResponseVO.buildSuccess(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO issueCoupon(int couponId, int userId) {
        try {
            couponMapper.insertCouponUser(couponId,userId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO issueCoupons(int couponId, int[] userIds) {
        try {
            for (int i=0;i<userIds.length;i++) {
                couponMapper.insertCouponUser(couponId, userIds[i]);
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO getCoupons() {
        try {
            List<Coupon> coupons1= couponMapper.selectCoupon();
            List<Coupon> coupons=new ArrayList<>();
            for(Coupon coupon:coupons1){
                if(coupon.getEndTime().before(new Timestamp(System.currentTimeMillis()))){

                }
                else {
                    coupons.add(coupon);
                }
            }
            return ResponseVO.buildSuccess(coupons);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public List<Coupon> selectCouponByUser(int userId) {
        return couponMapper.selectCouponByUser(userId);
    }

    @Override
    public Coupon selectById(int id) {
        return couponMapper.selectById(id);
    }

    @Override
    public void deleteCouponUser(int couponId, int userId) {
        couponMapper.deleteCouponUser(couponId,userId);
    }

    @Override
    public void insertCouponUser(int couponId, int userId) {
        couponMapper.insertCouponUser(couponId,userId);
    }
}
