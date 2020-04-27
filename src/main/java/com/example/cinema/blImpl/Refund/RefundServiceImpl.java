package com.example.cinema.blImpl.Refund;

import com.example.cinema.bl.Refund.RefundService;
import com.example.cinema.data.Refund.RefundMapper;
import com.example.cinema.po.RefundSchedule;
import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;

@Service
public class RefundServiceImpl implements RefundService ,RefundServiceForBL{

    @Autowired
    RefundMapper refundMapper;

    @Override
    public ResponseVO addRefundMethod(RefundForm refundForm) {
        try {
            RefundSchedule refundSchedule = refundForm.getRefundSchedule();
            refundMapper.insertRefundSchedule(refundSchedule);
            return ResponseVO.buildSuccess();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("添加失败");
        }
    }

    /*
    用于updateRefundMethod的方法
     */
    private ResponseVO addWithId(RefundForm refundForm,int id) {
        try {
            RefundSchedule refundSchedule = refundForm.getRefundSchedule();
            //手动设置id
            refundSchedule.setId(id);
//            System.out.println("schedule:"+refundSchedule.getBestTime()+" "+refundForm.getBestPercent());
            refundMapper.insertRefundSchedule(refundSchedule);
            return ResponseVO.buildSuccess();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("添加失败");
        }
    }

    @Override
    public ResponseVO deleteRefundMethod(int RefundId) {
        try {
            refundMapper.deleteRefundSchedule(RefundId);
            return ResponseVO.buildSuccess();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("删除失败");
        }
    }

    @Override
    public ResponseVO getAllRefundMethods() {
        try {
            List<RefundSchedule> refundSchedules = refundMapper.selectAllSchedules();
//            List<RefundSchedule> refundSchedules = selectAllSchedules();
            return ResponseVO.buildSuccess(refundSchedules);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("加载失败");
        }
    }

    @Override
    public ResponseVO updateRefundMethod(int RefundId,RefundForm refundForm) {
        deleteRefundMethod(RefundId);
        addWithId(refundForm, RefundId);
        return ResponseVO.buildSuccess();
    }

    /*
        实现serviceForBL，用于退票时调用端直接获得退钱比例，降低耦合度
     */
    @Override
    public double getRefundPercent(int Id, double timeRemain){
        RefundSchedule refundSchedule = refundMapper.selectRefundById(Id);
//        RefundSchedule refundSchedule = selectRefundById(Id);

        if (timeRemain>=refundSchedule.getBestTime()){
            return refundSchedule.getBestPercent();
        }
        else if (timeRemain>=refundSchedule.getMediumTime()){
            return refundSchedule.getMediumPercent();
        }
        else if (timeRemain>=refundSchedule.getBadTime()){
            return refundSchedule.getBadPercent();
        }
        else {
            return 0;
        }
    }
    /**--------------------------------以下为此类中依赖的Mapper方法的桩-----------------------------------------------**/
//    List<RefundSchedule> selectAllSchedules(){
//        ArrayList<RefundSchedule> arrayList = new ArrayList<>();
////        arrayList.add(new RefundSchedule(1,10,0.8,6,0.5,3,0.2));
////        arrayList.add(new RefundSchedule(2,6,0.7,3,0.2,2,0.1));
////        return arrayList;
////    }
//    RefundSchedule selectRefundById(int Id){return 1};

}
