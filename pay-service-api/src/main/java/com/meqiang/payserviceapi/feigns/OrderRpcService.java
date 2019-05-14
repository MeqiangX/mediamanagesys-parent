package com.meqiang.payserviceapi.feigns;

import com.dtstack.plat.lang.web.R;
import com.mingkai.systemcommon.model.Do.order.TicketDetailDo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description:  调用订单服务
 * @author: Created by 云风 on 2019-05-13 20:45
 */
@FeignClient(value = "order-service-api")
public interface OrderRpcService {


    @RequestMapping(value = "pay-success",method = RequestMethod.GET)
    R<Boolean> paySuccess(@RequestParam("orderId") String orderId);

    @RequestMapping(value = "get-orderby-orderId",method = RequestMethod.GET)
    R<TicketDetailDo> getOrderByOrderId(@RequestParam("orderId") String orderId);

}
