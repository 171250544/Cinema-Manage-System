package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPStrategyForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId, @RequestParam String name) {//Todo 增加了新的参数 category
        return vipService.addVIPCard(userId, name);
    }

    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId) {
        return vipService.getCardByUserId(userId);
    }

    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo() {
        return vipService.getVIPInfo();
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardForm vipCardForm) {
        return vipService.charge(vipCardForm);
    }

    @GetMapping("/getVipCategory")
    public ResponseVO getVipCategory() {
        return vipService.getAllVipCardStrategy();
    }

    @PostMapping("/addCategory")
    public ResponseVO addCategory(@RequestParam String name, @RequestParam int reach, @RequestParam int minus, @RequestParam int price) {
        return vipService.addVIPStrategy(name, reach, minus, price);
    }

    @PostMapping("/deleteCategory")
    public ResponseVO deleteCategory(@RequestParam String name) {
        return vipService.deleteCategory(name);
    }

    @PostMapping("/changeCategory")
    public ResponseVO changeCategory(@RequestParam String beforeName,@RequestParam String name, @RequestParam int reach, @RequestParam int minus, @RequestParam int price){return vipService.changeVIPStrategy(beforeName,name, reach, minus, price);}
}
