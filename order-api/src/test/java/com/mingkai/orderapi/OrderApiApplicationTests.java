package com.mingkai.orderapi;

import com.google.common.collect.Lists;
import com.mingkai.orderapi.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApiApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void contextLoads() {

        List<Integer> list = Lists.newArrayList();

        list.add(47);
        list.add(48);
        orderService.seatBooking(list,4);

    }

}
