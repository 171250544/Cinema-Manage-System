package com.example.cinema.blImpl.management.hall;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.cinema.bl.management.HallService;
import com.example.cinema.bl.management.ScheduleService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallForm;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.ScheduleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList){
        List<HallVO> hallVOList = new ArrayList<>();
        for(Hall hall : hallList){
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }

    @Override
    public ResponseVO insertHall(HallForm hallForm){
        try{
            hallMapper.insertHall(hallForm);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public void insertUseTime(int hallId, Date endTime){
        try {
            hallMapper.updateUseTime(hallId, endTime);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public ResponseVO canChangeHall(){
        try{
            List<Hall> halls = hallMapper.selectAllHall();
            List<HallVO> freeHalls = new ArrayList<>();
            //将空闲状态的影厅返回
            for(Hall hall : halls){
                if(hall.getEndTime() == null || hall.getEndTime().before(new Date())){
                    freeHalls.add(new HallVO(hall));
                }
            }
            ResponseVO res = ResponseVO.buildSuccess();
            res.setContent(freeHalls);
            return res;
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO changeInfo(HallVO hallVO){
        try{
            hallMapper.updateHallInfo(hallVO);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

}
