package com.meqiang.payserviceapi.service;

import com.dtstack.plat.lang.web.R;
import com.meqiang.payserviceapi.feigns.OrderRpcService;
import com.mingkai.systemcommon.model.Do.order.TicketDetailDo;
import com.mingkai.systemcommon.utils.check.CheckOfR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
