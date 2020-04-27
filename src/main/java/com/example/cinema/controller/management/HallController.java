package com.example.cinema.controller.management;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**影厅管理
 * @author fjj
 * @date 2019/4/12 1:59 PM
 */
@RestController
public class HallController {
    @Autowired
    private HallService hallService;

    @RequestMapping(value = "hall/all", method = RequestMethod.GET)
    public ResponseVO searchAllHall(){
        return hallService.searchAllHall();
    }

    @RequestMapping(value = "hall/new", method = RequestMethod.POST)
    public ResponseVO  addNewHall(@RequestBody HallForm hallForm) {
        return hallService.insertHall(hallForm);
    }

    @RequestMapping(value = "hall/canChange", method = RequestMethod.GET)
    public ResponseVO canChange(){
        return hallService.canChangeHall();
    }

    @RequestMapping(value = "hall/changeInfo", method = RequestMethod.POST)
    public ResponseVO changeInfo(@RequestBody HallVO hallVO){
        return hallService.changeInfo(hallVO);
    }


}
