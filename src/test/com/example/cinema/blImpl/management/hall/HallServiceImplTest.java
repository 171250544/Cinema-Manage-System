package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.vo.HallVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class HallServiceImplTest {

    @Autowired
    HallService hallService;

    @Test
    public void canChangeHall() {
        HallVO hall = new HallVO();
        hall.setId(2);
        List vo = (ArrayList) hallService.canChangeHall().getContent();
        HallVO freeHall = (HallVO)vo.get(0);
        assertEquals(hall.getId(), freeHall.getId());
    }
}