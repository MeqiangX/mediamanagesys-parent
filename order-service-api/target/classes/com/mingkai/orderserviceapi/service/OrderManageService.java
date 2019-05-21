package com.mingkai.orderserviceapi.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mappermodule.manager.TicketDetailManager;
import com.mingkai.mappermodule.model.Po.order.TicketSearchPo;
import com.mingkai.mappermodule.model.Vo.ticket.TicketDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-15 10:48
 */
@Service
public class OrderManageService {

    @Autowired
    private TicketDetailManager ticketDetailManager;


/**
     * 搜索用户订单
     * @param ticketSearchPo
     * @return
     */

    public Page<TicketDetailVo> orderSearch(TicketSearchPo ticketSearchPo){
        return ticketDetailManager.getBaseMapper().orderSearch(ticketSearchPo);
    }

}

