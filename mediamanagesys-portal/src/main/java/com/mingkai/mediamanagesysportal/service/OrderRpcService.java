package com.mingkai.mediamanagesysportal.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesysmapper.model.Po.order.CoordinatePo;
import com.mingkai.mediamanagesysmapper.model.Po.order.OrderPagePo;
import com.mingkai.mediamanagesysmapper.model.Vo.order.OrderSimpleVo;
import com.mingkai.mediamanagesysmapper.utils.check.CheckOfR;
import com.mingkai.mediamanagesysportal.feign.OrderRpcFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-25 20:24
 */
@Service
@Slf4j
public class OrderRpcService {

    @Autowired
    private OrderRpcFeign orderRpcFeign;

    /**
     * 查询用户的订单 分页
     * @param orderPagePo
     * @return
     */
    public Page<OrderSimpleVo> userOrders(OrderPagePo orderPagePo){
        return CheckOfR.check(orderRpcFeign.userOrders(orderPagePo));
    }

    public Boolean orderTest(){
        return CheckOfR.check(orderRpcFeign.orderTest());
    }


    /**
     * 修改 seatId 可以有多个
     * @param seatIds
     * @param userId
     * @return
     */
    public String seatBooking(List<Integer> seatIds,Integer userId){
        return CheckOfR.check(orderRpcFeign.seatBooking(seatIds,userId));
    }




    public OrderSimpleVo orderDetail(String orderId){
        return CheckOfR.check(orderRpcFeign.orderDetail(orderId));
    }



    public Long unpayOrderRestTime(String orderId){
        return CheckOfR.check(orderRpcFeign.unpayOrderRestTime(orderId));
    }


    public Boolean timeoutOrderCheck(String orderId){
        return CheckOfR.check(orderRpcFeign.timeoutOrderCheck(orderId));
    }


    public Boolean deleteUserOrders(Integer userId){
        return CheckOfR.check(orderRpcFeign.deleteUserOrders(userId));
    }


    public Boolean cancelOrder(String orderId){
        return CheckOfR.check(orderRpcFeign.cancelOrder(orderId));
    }


    /**
     * 查询用户在当前排场下已经购买的票数
     * @param userId
     * @param arrangeId
     * @return
     */
    public Integer boughtCountsArrangeId(Integer userId,String arrangeId){
        return CheckOfR.check(orderRpcFeign.boughtCountsArrangeId(userId,arrangeId));
    }


    public List<Integer> isAllowPurchased(List<Integer> seats){
        return CheckOfR.check(orderRpcFeign.isAllowPurchased(seats));
    }


    public List<Integer> seatIdsByCoordinate(CoordinatePo coordinatePo){
        return CheckOfR.check(orderRpcFeign.seatIdsByCoordinate(coordinatePo));
    }



    public Boolean sendOrderSuccess(String orderId){
        return CheckOfR.check(orderRpcFeign.sendOrderSuccess(orderId));
    }

}
