package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @GetMapping("{userId}/get")
    public ResponseVO getCoupons(@PathVariable int userId){
        return couponService.getCouponsByUser(userId);
    }

    @GetMapping("/getall")
    public ResponseVO getCouponsForIssue(){
        return couponService.getCoupons();
    }

    @PostMapping("/issue")
    public ResponseVO issueCoupons(@RequestParam int[] userids,@RequestParam int couponID){
        return couponService.issueCoupons(couponID,userids);
    }
}
