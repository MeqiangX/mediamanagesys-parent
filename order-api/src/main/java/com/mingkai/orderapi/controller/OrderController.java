package com.mingkai.orderapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesysmapper.common.API;
import com.mingkai.mediamanagesysmapper.model.Po.order.CoordinatePo;
import com.mingkai.mediamanagesysmapper.model.Po.order.OrderPagePo;
import com.mingkai.mediamanagesysmapper.model.Vo.order.OrderSimpleVo;
import com.mingkai.orderapi.service.OrderService;
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
    public R<Page<OrderSimpleVo>> userOrders(@RequestBody OrderPagePo orderPagePo){
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
    public R<String> seatBooking(@RequestParam("seatIds[]") List<Integer> seatIds, @RequestParam("userId") Integer userId){
        return new APITemplate<String>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected String process() throws BizException {
                return orderService.seatBooking(seatIds,userId);
            }
        }.execute();
    }



    @ApiOperation("通过订单id查找未支付订单信息")
    @RequestMapping("order-detail/{orderId}")
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
    @RequestMapping("unpay-order-rest-time/{orderId}")
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
    @RequestMapping("timeout-order-check/{orderId}")
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

    @ApiOperation("取消用户的所有订单")
    @GetMapping("delete-user-orders")
    public R<Boolean> deleteUserOrders(@RequestParam("userId") Integer userId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return orderService.deleteUserOrders(userId);
            }
        }.execute();
    }

    @ApiOperation("取消订单(未支付和已支付)")
    @GetMapping("cancel-order")
    public R<Boolean> cancelOrder(@RequestParam("orderId") String orderId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return orderService.cancelOrder(orderId);
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
    public R<Integer> boughtCountsArrangeId(@RequestParam("userId") Integer userId,
                                            @RequestParam("arrangeId") String arrangeId){
        return new APITemplate<Integer>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Integer process() throws BizException {
                return orderService.boughtCountsArrangeId(userId,arrangeId);
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
                return orderService.isAllowPurchased(seats);
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
                return orderService.seatIdsByCoordinate(coordinatePo);
            }
        }.execute();
    }


    @ApiOperation("发送购票成功短信")
    @GetMapping("send-order-success")
    public R<Boolean> sendOrderSuccess(@RequestParam("orderId") String orderId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return orderService.sendOrderSuccessMessage(orderId);
            }
        }.execute();
    }

}
