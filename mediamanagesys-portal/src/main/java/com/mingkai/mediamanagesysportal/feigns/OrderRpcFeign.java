package com.mingkai.mediamanagesysportal.feigns;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.web.R;
import com.mingkai.systemcommon.model.Do.order.TicketDetailDo;
import com.mingkai.systemcommon.model.Po.order.CoordinatePo;
import com.mingkai.systemcommon.model.Po.order.OrderPagePo;
import com.mingkai.systemcommon.model.Vo.order.OrderSimpleVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-13 21:57
 */
@FeignClient(value = "order-service-api")
@Repository
public interface OrderRpcFeign {

    /**
     * 查询用户的订单 分页
     * @param orderPagePo
     * @return
     */
    @ApiOperation("查询用户的订单")
    @PostMapping("user-orders")
    R<Page<OrderSimpleVo>> userOrders(OrderPagePo orderPagePo);

    @ApiOperation("购票测试")
    @GetMapping("order-test")
    R<Boolean> orderTest();

    /**
     * 修改 seatId 可以有多个
     * @param seatIds
     * @param userId
     * @return
     */
    @ApiOperation("订座")
    @GetMapping("seat-book")
    R<String> seatBooking(@RequestParam("seatIds[]") List<Integer> seatIds, @RequestParam Integer userId);


    @ApiOperation("通过订单id查找未支付订单信息")
    @GetMapping("order-detail/{orderId}")
    R<OrderSimpleVo> orderDetail(@PathVariable String orderId);


    @ApiOperation("通过订单id来查找未支付订单的剩余支付时间")
    @GetMapping("unpay-order-rest-time/{orderId}")
    R<Long> unpayOrderRestTime(@PathVariable String orderId);


    @ApiOperation("对于过期为支付的订单的操作")
    @GetMapping("timeout-order-check/{orderId}")
    R<Boolean> timeoutOrderCheck(@PathVariable String orderId);

    @ApiOperation("取消用户的所有订单")
    @GetMapping("delete-user-orders")
    R<Boolean> deleteUserOrders(@RequestParam("userId") Integer userId);

    @ApiOperation("取消订单(未支付和已支付)")
    @GetMapping("cancel-order")
    R<Boolean> cancelOrder(@RequestParam("orderId") String orderId);


    /**
     * 查询用户在当前排场下已经购买的票数
     * @param userId
     * @param arrangeId
     * @return
     */
    @ApiOperation("当前排场下当前用户的已经购买票数")
    @GetMapping("bought-counts-arrangeId")
    R<Integer> boughtCountsArrangeId(@RequestParam("userId") Integer userId,@RequestParam("arrangeId") String arrangeId);

    @ApiOperation("当前要购买的坐席是否可买")
    @GetMapping("allow-purchased")
    R<List<Integer>> isAllowPurchased(@RequestParam("seats[]") List<Integer> seats);


    @ApiOperation("通过坐席坐标和排片id来查询坐席id")
    @PostMapping("/seatId-by-coordinate")
    R<List<Integer>> seatIdsByCoordinate(@RequestBody CoordinatePo coordinatePo);

    @ApiOperation("支付成功的操作")
    @GetMapping("/pay-success")
    R<Boolean> paySuccess(@RequestParam("orderId") String orderId);

    @ApiOperation("得到订单信息By orderId")
    @GetMapping("/get-orderby-orderId")
    R<TicketDetailDo> getOrderByOrderId(@RequestParam("orderId") String orderId);

}
