package com.meqiang.payserviceapi.service;

import com.meqiang.payserviceapi.feigns.OrderRpcService;
import com.mingkai.mappermodule.model.Do.order.TicketDetailDo;
import com.mingkai.mappermodule.utils.check.CheckOfR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: 支付Service
 * @author: Created by 云风 on 2019-05-13 20:56
 */
@Service
public class ApiPayService {

    @Autowired
    private OrderRpcService orderRpcService;

    public Boolean paySuccess(String orderId){
        return CheckOfR.check(orderRpcService.paySuccess(orderId));
    }

    public TicketDetailDo getOrderByOrderId(String orderId){
        return CheckOfR.check(orderRpcService.getOrderByOrderId(orderId));
    }

}
