package com.mingkai.mediamanagesysbackend.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.web.R;
import com.mingkai.mediamanagesysmapper.common.API;
import com.mingkai.mediamanagesysmapper.model.Po.order.TicketSearchPo;
import com.mingkai.mediamanagesysmapper.model.Vo.ticket.TicketDetailVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-25 20:20
 */
@FeignClient(value = "order-api")
@Repository
public interface OrderManageRpcFeign {

    @ApiOperation("订单管理")
    @RequestMapping(value = API.API_ORDERMANAGE + "/backend-order",method = RequestMethod.GET)
    R<Boolean> backendOrder();


    @ApiOperation("查询用户订单(分页/条件)")
    @RequestMapping(value = API.API_ORDERMANAGE + "/order-search",method = RequestMethod.POST)
    R<Page<TicketDetailVo>> orderSearch(@RequestBody TicketSearchPo ticketSearchPo);

}
