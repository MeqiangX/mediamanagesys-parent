package com.mingkai.mediamanagesysbackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mappermodule.model.Po.order.TicketSearchPo;
import com.mingkai.mappermodule.model.Vo.ticket.TicketDetailVo;
import com.mingkai.mappermodule.utils.check.CheckOfR;
import com.mingkai.mediamanagesysbackend.feigns.OrderManageRpcFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-15 16:02
 */
@Service
public class OrderManageRpcService {

    @Autowired
    private OrderManageRpcFeign orderManageRpcFeign;


    public Boolean backendOrder(){
        return CheckOfR.check(orderManageRpcFeign.backendOrder());
    }


    public Page<TicketDetailVo> orderSearch(TicketSearchPo ticketSearchPo){
        return CheckOfR.check(orderManageRpcFeign.orderSearch(ticketSearchPo));
    }



}
