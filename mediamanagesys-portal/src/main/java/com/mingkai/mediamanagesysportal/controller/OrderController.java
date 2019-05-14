package com.mingkai.mediamanagesysportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesysportal.service.OrderRpcService;
import com.mingkai.systemcommon.common.API;
import com.mingkai.systemcommon.model.Po.order.CoordinatePo;
import com.mingkai.systemcommon.model.Po.order.OrderPagePo;
import com.mingkai.systemcommon.model.Vo.order.OrderSimpleVo;
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
    private OrderRpcService orderRpcService;


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
                return orderRpcService.userOrders(orderPagePo);
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
    public R<String> seatBooking(@RequestParam("seatIds[]") List<Integer> seatIds, @RequestParam Integer userId){
        return new APITemplate<String>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected String process() throws BizException {
                return orderRpcService.seatBooking(seatIds,userId);
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
                return orderRpcService.orderDetail(orderId);
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
                return orderRpcService.unpayOrderRestTime(orderId);
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
                return orderRpcService.timeoutOrderCheck(orderId);
            }
        }.execute();
    }

    @ApiOperation("取消用户的所有订单")
    @GetMapping("delete-user-orders")
    public R<Boolean> deleteUserOrders(Integer userId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return orderRpcService.deleteUserOrders(userId);
            }
        }.execute();
    }

    @ApiOperation("取消订单(未支付和已支付)")
    @GetMapping("cancel-order")
    public R<Boolean> cancelOrder(String orderId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return orderRpcService.cancelOrder(orderId);
            }
        }.execute();
    }


    /**
     * 查询用户在当前排场下已经购买的票数
     * @param userId
     * @param arrangeId
     * @return
     */
    @ApiOperation("当前排场下当前用户的已经购买票数")
    @GetMapping("bought-counts-arrangeId")
    public R<Integer> boughtCountsArrangeId(Integer userId,String arrangeId){
        return new APITemplate<Integer>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Integer process() throws BizException {
                return orderRpcService.boughtCountsArrangeId(userId,arrangeId);
            }
        }.execute();
    }

    @ApiOperation("当前要购买的坐席是否可买")
    @GetMapping("allow-purchased")
    public R<List<Integer>> isAllowPurchased(@RequestParam("seats[]") List<Integer> seats){
        return new APITemplate<List<Integer>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<Integer> process() throws BizException {
                return orderRpcService.isAllowPurchased(seats);
            }
        }.execute();
    }


    @ApiOperation("通过坐席坐标和排片id来查询坐席id")
    @PostMapping("/seatId-by-coordinate")
    public R<List<Integer>> seatIdsByCoordinate(@RequestBody CoordinatePo coordinatePo){
        return new APITemplate<List<Integer>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<Integer> process() throws BizException {
                return orderRpcService.seatIdsByCoordinate(coordinatePo);
            }
        }.execute();
    }
}
