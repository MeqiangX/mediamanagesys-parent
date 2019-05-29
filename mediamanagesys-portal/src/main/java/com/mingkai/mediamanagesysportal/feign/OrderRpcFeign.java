package com.mingkai.mediamanagesysportal.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.web.R;
import com.mingkai.mediamanagesysmapper.common.API;
import com.mingkai.mediamanagesysmapper.model.Po.order.CoordinatePo;
import com.mingkai.mediamanagesysmapper.model.Po.order.OrderPagePo;
import com.mingkai.mediamanagesysmapper.model.Vo.order.OrderSimpleVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-25 20:11
 */
@FeignClient(value = "order-api")
@Repository
public interface OrderRpcFeign {


    /**
     * 查询用户的订单 分页
     * @param orderPagePo
     * @return
     */
    @ApiOperation("查询用户的订单")
    @RequestMapping(value = API.API_ORDER+ "/user-orders",method = RequestMethod.POST)
    R<Page<OrderSimpleVo>> userOrders(@RequestBody OrderPagePo orderPagePo);

    @ApiOperation("购票测试")
    @RequestMapping(value = API.API_ORDER + "/order-test",method = RequestMethod.GET)
    R<Boolean> orderTest();


    /**
     * 修改 seatId 可以有多个
     * @param seatIds
     * @param userId
     * @return
     */
    @ApiOperation("订座")
    @RequestMapping(value = API.API_ORDER + "/seat-book",method = RequestMethod.POST)
    R<String> seatBooking(@RequestBody List<Integer> seatIds, @RequestParam("userId") Integer userId);



    @ApiOperation("通过订单id查找未支付订单信息")
    @RequestMapping(value = API.API_ORDER + "/order-detail/{orderId}")
    R<OrderSimpleVo> orderDetail(@PathVariable String orderId);


    @ApiOperation("通过订单id来查找未支付订单的剩余支付时间")
    @RequestMapping(value = API.API_ORDER + "/unpay-order-rest-time/{orderId}")
    R<Long> unpayOrderRestTime(@PathVariable String orderId);


    @ApiOperation("对于过期为支付的订单的操作")
    @RequestMapping(value = API.API_ORDER + "/timeout-order-check/{orderId}")
    R<Boolean> timeoutOrderCheck(@PathVariable String orderId);

    @ApiOperation("取消用户的所有订单")
    @RequestMapping(value = API.API_ORDER + "/delete-user-orders",method = RequestMethod.GET)
    R<Boolean> deleteUserOrders(@RequestParam("userId") Integer userId);

    @ApiOperation("取消订单(未支付和已支付)")
    @RequestMapping(value = API.API_ORDER + "/cancel-order",method = RequestMethod.GET)
    R<Boolean> cancelOrder(@RequestParam("orderId") String orderId);


    /**
     * 查询用户在当前排场下已经购买的票数
     * @param userId
     * @param arrangeId
     * @return
     */
    @ApiOperation("当前排场下当前用户的已经购买票数")
    @RequestMapping(value = API.API_ORDER + "/bought-counts-arrangeId",method = RequestMethod.GET)
    R<Integer> boughtCountsArrangeId(@RequestParam("userId") Integer userId,@RequestParam("arrangeId") String arrangeId);

    @ApiOperation("当前要购买的坐席是否可买")
    @RequestMapping(value = API.API_ORDER + "/allow-purchased",method = RequestMethod.POST)
    R<List<Integer>> isAllowPurchased(@RequestBody List<Integer> seats);

    @ApiOperation("通过坐席坐标和排片id来查询坐席id")
    @RequestMapping(value = API.API_ORDER + "/seatId-by-coordinate",method = RequestMethod.POST)
    R<List<Integer>> seatIdsByCoordinate(@RequestBody CoordinatePo coordinatePo);


    @ApiOperation("发送购票成功短信")
    @RequestMapping(value = API.API_ORDER + "/send-order-success",method = RequestMethod.GET)
    R<Boolean> sendOrderSuccess(@RequestParam("orderId") String orderId);


}
