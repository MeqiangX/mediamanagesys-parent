package com.mingkai.mediamanagesysportal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.common.API;
import com.mingkai.mediamanagesyscommon.model.Po.order.OrderPagePo;
import com.mingkai.mediamanagesyscommon.model.Vo.order.OrderSimpleVo;
import com.mingkai.mediamanagesysportal.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    /**
     * 查询用户的订单 分页
     * @param orderPagePo
     * @return
     */
    @ApiOperation("查询用户的订单")
    @PostMapping("user-orders")
    public R<Page<OrderSimpleVo>> userOrders(OrderPagePo orderPagePo){
        return new APITemplate<Page<OrderSimpleVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<OrderSimpleVo> process() throws BizException {
                return orderService.userOrders(orderPagePo);
            }
        }.execute();
    }

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


    /**
     * 修改 seatId 可以有多个
     * @param seatIds
     * @param userId
     * @return
     */
    @ApiOperation("订座")
    @GetMapping("seat-book")
    public R<Boolean> seatBooking(@RequestParam List<Integer> seatIds, @RequestParam Integer userId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return orderService.seatBooking(seatIds,userId);
            }
        }.execute();
    }



    @ApiOperation("通过订单id查找未支付订单信息")
    @GetMapping("order-detail/{orderId}")
    public R<OrderSimpleVo> orderDetail(@PathVariable String orderId){
        return new APITemplate<OrderSimpleVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected OrderSimpleVo process() throws BizException {
                return orderService.orderDetail(orderId);
            }
        }.execute();
    }


    @ApiOperation("通过订单id来查找未支付订单的剩余支付时间")
    @GetMapping("unpay-order-rest-time/{orderId}")
    public R<Long> unpayOrderRestTime(@PathVariable String orderId){
        return new APITemplate<Long>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Long process() throws BizException {
                return orderService.getUnPayOrderTime(orderId);
            }
        }.execute();
    }


    @ApiOperation("对于过期为支付的订单的操作")
    @GetMapping("timeout-order-check/{orderId}")
    public R<Boolean> timeoutOrderCheck(@PathVariable String orderId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return orderService.timeoutOrderCheck(orderId);
            }
        }.execute();
    }
}
