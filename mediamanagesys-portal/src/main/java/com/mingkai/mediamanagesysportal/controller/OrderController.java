package com.mingkai.mediamanagesysportal.controller;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.common.API;
import com.mingkai.mediamanagesysportal.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 购票Controller
 * @author: Created by 云风 on 2019-04-12 15:04
 */
@RestController
@RequestMapping(API.API_ORDER)
@Api(tags = API.PROTAL_ORDER)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("购票测试")
    @GetMapping("order-test")
    public R<Boolean> orderTest(){
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


    @ApiOperation("订座")
    @GetMapping("seat-book")
    public R<Boolean> seatBooking(@RequestParam("seatId") Integer seatId,
                                  @RequestParam("userId") Integer userId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return orderService.seatBooking(seatId,userId);
            }
        }.execute();
    }

}
