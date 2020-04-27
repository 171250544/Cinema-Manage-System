package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.po.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TicketServiceImplTest {

    @Autowired
    TicketService ticketService;


    @Test
    public void cancelTicket() {
        List<Integer> ids = new ArrayList<>();
        for(int i=110; i<111; i++){
            ids.add(i);
        }
        assertTrue(ticketService.cancelTicket(ids).getSuccess());
    }
}