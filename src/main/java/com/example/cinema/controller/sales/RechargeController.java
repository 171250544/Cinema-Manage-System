package com.example.cinema.controller.sales;

import com.example.cinema.bl.record.RechargeService;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 黄健 on 2019/6/11.
 */
@RestController
@RequestMapping("/recharge")
public class RechargeController {
    @Autowired
    RechargeService rechargeService;

    @GetMapping("/get/{userId}")
    public ResponseVO getTicketByUserId(@PathVariable int userId){
        return rechargeService.getRechargeByUser(userId);
    }

}
