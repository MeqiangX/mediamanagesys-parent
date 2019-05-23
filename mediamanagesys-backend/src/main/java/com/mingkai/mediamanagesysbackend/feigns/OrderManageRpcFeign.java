package com.mingkai.mediamanagesysbackend.feigns;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.web.R;
import com.mingkai.mappermodule.model.Po.order.TicketSearchPo;
import com.mingkai.mappermodule.model.Vo.ticket.TicketDetailVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-15 15:57
 */
@FeignClient(value = "order-service-api")
@Repository
public interface OrderManageRpcFeign {

    @ApiOperation("订单管理")
    @GetMapping("backend-order")
    R<Boolean> backendOrder();


    @ApiOperation("查询用户订单(分页/条件)")
    @PostMapping("order-search")
    R<Page<TicketDetailVo>> orderSearch(@RequestBody TicketSearchPo ticketSearchPo);


}
