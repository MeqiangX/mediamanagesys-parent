package com.mingkai.mediamanagesysportal.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mappermodule.model.Do.order.TicketDetailDo;
import com.mingkai.mappermodule.model.Po.order.CoordinatePo;
import com.mingkai.mappermodule.model.Po.order.OrderPagePo;
import com.mingkai.mappermodule.model.Vo.order.OrderSimpleVo;
import com.mingkai.mappermodule.utils.check.CheckOfR;
import com.mingkai.mediamanagesysportal.feigns.OrderRpcFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-13 21:59
 */
@Service
public class OrderRpcService {


    @Autowired
    private OrderRpcFeign orderRpcFeign;


    public Page<OrderSimpleVo> userOrders(OrderPagePo orderPagePo){
        return CheckOfR.check(orderRpcFeign.userOrders(orderPagePo));
    }


    public Boolean orderTest(){
        return CheckOfR.check(orderRpcFeign.orderTest());
    }


    public String seatBooking(List<Integer> seatIds, Integer userId){
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


    public Integer boughtCountsArrangeId(Integer userId,String arrangeId){
        return CheckOfR.check(orderRpcFeign.boughtCountsArrangeId(userId,arrangeId));
    }


    public List<Integer> isAllowPurchased(List<Integer> seats){
        return CheckOfR.check(orderRpcFeign.isAllowPurchased(seats));
    }



    public List<Integer> seatIdsByCoordinate(CoordinatePo coordinatePo){
        return CheckOfR.check(orderRpcFeign.seatIdsByCoordinate(coordinatePo));
    }


    public Boolean paySuccess(String orderId){
        return CheckOfR.check(orderRpcFeign.paySuccess(orderId));
    }


    public TicketDetailDo getOrderByOrderId(String orderId){
        return CheckOfR.check(orderRpcFeign.getOrderByOrderId(orderId));
    }


}
