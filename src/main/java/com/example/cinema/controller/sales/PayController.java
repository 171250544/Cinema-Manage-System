package com.example.cinema.controller.sales;

import com.example.cinema.bl.record.PayService;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 黄健 on 2019/6/11.
 */
@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    PayService payService;

    @GetMapping("/get/{userId}")
    public ResponseVO getTicketByUserId(@PathVariable int userId){
        return payService.getPayByUser(userId);
    }

    @GetMapping("/getuser/{total}")
    public ResponseVO getUserByTotal(@PathVariable double total){
       return payService.getUserByTotal(total);
    }

}
