package com.mingkai.mediamanagesysbackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesysbackend.feign.OrderManageRpcFeign;
import com.mingkai.mediamanagesysmapper.model.Po.order.TicketSearchPo;
import com.mingkai.mediamanagesysmapper.model.Vo.ticket.TicketDetailVo;
import com.mingkai.mediamanagesysmapper.utils.check.CheckOfR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-25 20:22
 */
@Service
@Slf4j
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
