package com.mingkai.orderapi;

import com.mingkai.mediamanagesysmapper.model.Po.order.OrderPagePo;
import com.mingkai.orderapi.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApiApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    public void contextLoads() {

        orderService.userOrders(new OrderPagePo());

    }

}