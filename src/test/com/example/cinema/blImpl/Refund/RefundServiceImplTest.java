package com.example.cinema.blImpl.Refund;

import com.example.cinema.bl.Refund.RefundService;
import com.example.cinema.po.RefundSchedule;
import com.example.cinema.vo.RefundForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RefundServiceImplTest {

    @Autowired
    RefundServiceImpl refundService;

    @Test
    public void addRefundMethod() {
        RefundForm refundSchedule = new RefundForm();
        refundSchedule.setBadPercent(0.1);
        refundSchedule.setBadTime(1);
        refundSchedule.setMediumPercent(0.5);
        refundSchedule.setMediumTime(5);
        refundSchedule.setBestPercent(0.7);
        refundSchedule.setBestTime(10);
        assertTrue(refundService.addRefundMethod(refundSchedule).getSuccess());
    }


    @Test
    public void getAllRefundMethods() {
        List res = (ArrayList)refundService.getAllRefundMethods().getContent();
        assertEquals(2, res.size());
    }

    @Test
    public void getRefundPercent() {
        assertEquals(0.5, refundService.getRefundPercent(1, 6.5), 0.001);
    }
}