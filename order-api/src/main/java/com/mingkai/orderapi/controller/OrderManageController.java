package com.mingkai.orderapi.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesysmapper.common.API;
import com.mingkai.mediamanagesysmapper.model.Po.order.TicketSearchPo;
import com.mingkai.mediamanagesysmapper.model.Vo.ticket.TicketDetailVo;
import com.mingkai.orderapi.service.OrderManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-15 10:13
 */
@RestController
@RequestMapping(API.API_ORDERMANAGE)
@Api(tags = API.BACKEND_ORDER)
public class OrderManageController {

    @Autowired
    private OrderManageService orderManageService;

    @ApiOperation("订单管理")
    @GetMapping("backend-order")
    public R<Boolean> backendOrder(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return Boolean.TRUE;
            }
        }.execute();
    }


    @ApiOperation("查询用户订单(分页/条件)")
    @PostMapping("order-search")
    public R<Page<TicketDetailVo>> orderSearch(@RequestBody TicketSearchPo ticketSearchPo){
        return new APITemplate<Page<TicketDetailVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<TicketDetailVo> process() throws BizException {
                return orderManageService.orderSearch(ticketSearchPo);
            }
        }.execute();
    }
}