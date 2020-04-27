package com.example.cinema.blImpl.record;

import com.example.cinema.vo.PayRecordVO;
import com.example.cinema.vo.UserForCouponVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServicelmplTest {

    @Autowired
    PayServicelmpl payServicelmpl;

    @Test
    public void getPayByUser() {
        ArrayList res = (ArrayList) payServicelmpl.getPayByUser(3).getContent();
        String total = "";
        for(Object payment: res){
            PayRecordVO u = (PayRecordVO) payment;
            total += u.getPrice();
        }
        assertEquals("40.0120.070.030.030.030.00.010.010.010.050.010.010.010.010.030.00.030.00.00.00.030.0-28.50.0", total);
    }

    @Test
    public void getUserByTotal() {
        ArrayList res = (ArrayList) payServicelmpl.getUserByTotal(100).getContent();
        String total = "";
        for(Object payment: res){
            UserForCouponVO u = (UserForCouponVO) payment;
            total += u.getHavepayed();
        }
        assertEquals("501.5", total);
    }
}