package com.example.cinema.controller.Refund;


import com.example.cinema.bl.Refund.RefundService;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Refund")
public class RefundController {

    @Autowired
    RefundService refundService;

    @PostMapping("/add")
    public ResponseVO addRefundMethod(@RequestBody RefundForm refundForm){
//        System.out.println(refundForm.getBestTime()+" "+refundForm.getBestPercent()+" "+refundForm.getMediumTime()+" "+refundForm.getMediumPercent()+" "+refundForm.getBadTime()+" "+refundForm.getBadPercent());
        return refundService.addRefundMethod(refundForm);
    }

    @RequestMapping("/delete")
    public ResponseVO deleteRefundMethod(@RequestParam int RefundId){
        return refundService.deleteRefundMethod(RefundId);
    }

    @RequestMapping("/seletALL")
    public ResponseVO getAllRefundMethods(){
        return refundService.getAllRefundMethods();
    }

    @RequestMapping("/update")
    public ResponseVO updateRefundMethod(@RequestParam int RefundId,@RequestBody RefundForm refundForm){
        return refundService.updateRefundMethod(RefundId,refundForm);
    }
}
