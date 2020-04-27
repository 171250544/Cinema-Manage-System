package com.example.cinema.blImpl.sales;

import com.example.cinema.blImpl.promotion.VipServiceForBL;
import com.example.cinema.blImpl.record.PayServiceForBL;
import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.Refund.RefundServiceForBL;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.blImpl.promotion.activityServiceForBL;
import com.example.cinema.blImpl.promotion.couponServiceForBL;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    couponServiceForBL couponService;
    @Autowired
    activityServiceForBL activityService;
    @Autowired
    PayServiceForBL payServiceForBL;
    @Autowired
    RefundServiceForBL refundService;
    @Autowired
    VipServiceForBL vipService;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        try {
            List<TicketVO> ticketVOS=new ArrayList<>();
            double total=0;
            for(int i=0;i<ticketForm.getSeats().size();i++) {
                Ticket ticket = new Ticket();
                ticket.setUserId(ticketForm.getUserId());
                ticket.setScheduleId(ticketForm.getScheduleId());
                ticket.setColumnIndex(ticketForm.getSeats().get(i).getColumnIndex());
                ticket.setRowIndex(ticketForm.getSeats().get(i).getRowIndex());
                ticket.setState(0);
                ticket.setPrice(0);
                ticketMapper.insertTicket(ticket);
                ticketVOS.add(new TicketVO(ticket));
                total=total+scheduleService.getScheduleItemById(ticket.getScheduleId()).getFare();
            }
            TicketWithCouponVO vo=new TicketWithCouponVO();
            vo.setTicketVOList(ticketVOS);
            vo.setActivities(activityService.selectActivities());
            vo.setCoupons(couponService.selectCouponByUser(ticketForm.getUserId()));
            vo.setTotal(total);
            return ResponseVO.buildSuccess(vo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeTicket(List<Integer> id, int couponId) {
        try {
            Boolean issue=Boolean.FALSE;
            double price = 0;
            List<Ticket> tickets=new ArrayList<>();
            for (Integer i : id) {
                tickets.add(ticketMapper.selectTicketById(i));
            }
            Coupon coupon = couponService.selectById(couponId);//优惠卷
            List<Activity> activities = activityService.selectActivities();//活动
            //算无优惠总价
            for (Ticket ticket : tickets) { price = price + scheduleService.getScheduleItemById(ticket.getScheduleId()).getFare(); }
            //比较优惠时间和额定价格
            Timestamp timestamps = tickets.get(0).getTime();
            if (coupon != null && timestamps.after(coupon.getStartTime()) && ( timestamps).before(coupon.getEndTime()) && price >= coupon.getTargetAmount()) {
                price = price - coupon.getDiscountAmount();
            }
            couponService.deleteCouponUser(couponId,ticketMapper.selectTicketById(id.get(0)).getUserId());
            //将最终价格存入票，改票状态，将票存入数据库
            double pricePerTicket = price/tickets.size();
            for (Ticket ticket:  tickets) {
                ticket.setPrice(pricePerTicket);
                ticket.setState(1);
                ticketMapper.deleteTicket(ticket.getId());
                ticketMapper.insertTicket(ticket);
            }

            //增加消费记录
            PayRecord payRecord=new PayRecord();
            payRecord.setPrice(price);
            payRecord.setTimestamp(new Date());
            payRecord.setTypeOfRecharge(0);
            payRecord.setUserId(tickets.get(0).getUserId());
            payRecord.setMoreOfPay(0);
            payServiceForBL.insertPayRecord(payRecord);
            //比较活动时间并赠送优惠卷
            for (Activity activity: activities) {
                if (timestamps.before(activity.getEndTime()) && timestamps.after(activity.getStartTime())&&coupon==null){
                    Coupon coupon1 = activity.getCoupon();
                    couponService.insertCouponUser(coupon1.getId(),tickets.get(0).getUserId());
                    issue=true;
                }
            }

            return ResponseVO.buildSuccess(issue);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);
            Hall hall=hallService.getHallById(schedule.getHallId());
            int[][] seats=new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        try{
            List<Ticket> tickets = ticketMapper.selectTicketByUser(userId);
            List<TicketWithScheduleVO> TwSVO = new ArrayList<>();
            for(Ticket t: tickets) {
                //获取该票的拍片信息
                ScheduleItem scheduleItem = scheduleService.getScheduleItemById(t.getScheduleId());
                if (scheduleItem != null) {
                    TwSVO.add(new TicketWithScheduleVO(t, scheduleItem));
                }
            }
            return ResponseVO.buildSuccess(TwSVO);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(List<Integer> id, int couponId) {
        try {
            boolean issue=false;
            //System.out.println("1");
            double price = 0;
            List<Ticket> tickets = new ArrayList<Ticket>();
            for (Integer i : id) { tickets.add(ticketMapper.selectTicketById(i)); }
            Coupon coupon = couponService.selectById(couponId);//优惠卷
            List<Activity> activities = activityService.selectActivities();//活动
            //算无优惠总价
            for (Ticket ticket : tickets) { price = price + scheduleService.getScheduleItemById(ticket.getScheduleId()).getFare(); }
            //比较优惠时间和额定价格
            Timestamp timestamps = tickets.get(0).getTime();
            if ( coupon != null && timestamps.after(coupon.getStartTime()) && timestamps.before(coupon.getEndTime()) && price >= coupon.getTargetAmount()) {
                price = price - coupon.getDiscountAmount();
            }
            //vip扣钱
            VIPCard vipCard = vipService.selectCardByUserId(tickets.get(0).getUserId());
            if (vipCard.getBalance()>=price){
                     vipService.updateBalance(vipCard.getId(),(vipCard.getBalance()-price));
            }
            else {return ResponseVO.buildFailure("余额不足！");}
            //比较活动时间并赠送优惠卷
            for (Activity activity: activities) {
                if (timestamps.before(activity.getEndTime()) && timestamps.after(activity.getStartTime())&&coupon==null){
                    CouponForm couponForm = new CouponForm();
                    Coupon coupon1 = activity.getCoupon();
                    couponService.insertCouponUser(coupon1.getId(),tickets.get(0).getUserId());
                    issue=true;
                }
            }
            couponService.deleteCouponUser(couponId,ticketMapper.selectTicketById(id.get(0)).getUserId());

            //将最终价格存入票，改票状态，将票存入数据库
            double pricePerTicket = price/tickets.size();
            for (Ticket ticket:  tickets) {
               // System.out.println("1");
                ticket.setPrice(pricePerTicket);
                ticket.setState(1);
                ticketMapper.deleteTicket(ticket.getId());
                ticketMapper.insertTicket(ticket);
            }

            //增加消费记录
            PayRecord payRecord=new PayRecord();
            payRecord.setPrice(price);
            payRecord.setTimestamp(new Date());
            payRecord.setTypeOfRecharge(1);
            payRecord.setUserId(tickets.get(0).getUserId());
            payRecord.setMoreOfPay(0);
            payServiceForBL.insertPayRecord(payRecord);
            return ResponseVO.buildSuccess(issue);
        }
        catch (Exception e){
            e.printStackTrace();
            return  ResponseVO.buildFailure("失败");
        }

    }

    @Override
    public ResponseVO cancelTicket(List<Integer> id) {
        try {
            Ticket ticket;
            int userId;
            VIPCard vipCard;
            ScheduleItem scheduleItem;
            double refundPercent;
            double timeRemain;

            //循环取消每张票
            for (Integer i : id) {
                ticket = ticketMapper.selectTicketById(i);
                userId = ticket.getUserId();
                scheduleItem = scheduleService.getScheduleItemById(ticket.getScheduleId());

                //得到当前距离电影开场的时间，用于计算退钱比例
                timeRemain = (double) ((scheduleItem.getStartTime().getTime()-new Date().getTime())/1000/60/60);
                refundPercent = refundService.getRefundPercent(scheduleItem.getRefundSchedule(), timeRemain);
                if (refundPercent == -1){ return ResponseVO.buildFailure("时间错误"); }

                //向vip卡中退钱
                double price = ticket.getPrice()*refundPercent;
                if ((vipCard = vipService.selectCardByUserId(userId)) != null){
                    vipService.updateBalance(vipCard.getId(),(vipCard.getBalance()+price));
                }
                else {
                    //非会员卡支付情况，需要返还进支付时使用的银行卡
                }

                //增加退票记录
                PayRecord payRecord = new PayRecord();
                payRecord.setPrice(-price);
                payRecord.setTimestamp(new Date());
                payRecord.setTypeOfRecharge(0);
                payRecord.setUserId(ticket.getUserId());
                payRecord.setMoreOfPay(1);
                payServiceForBL.insertPayRecord(payRecord);

                ticketMapper.deleteTicket(i);
            }
            return  ResponseVO.buildSuccess();
        }
        catch (Exception e){
            e.printStackTrace();
            return  ResponseVO.buildFailure("退票失败，cancelTicket方法出错");
        }
//        return null;
    }

}




