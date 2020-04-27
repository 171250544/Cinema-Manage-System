package com.example.cinema.blImpl.promotion;

import com.example.cinema.po.Coupon;
import java.util.List;

public interface couponServiceForBL {

    List<Coupon> selectCouponByUser(int userId);

    Coupon selectById(int id);

    void deleteCouponUser(int couponId,int userId);

    void insertCouponUser(int couponId,int userId);

}
