package com.mingkai.orderserviceapi.manager;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingkai.orderserviceapi.mapper.TicketDetailMapper;
import com.mingkai.orderserviceapi.model.Do.order.TicketDetailDo;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 15:46
 */
@Repository
public class TicketDetailManager extends ServiceImpl<TicketDetailMapper, TicketDetailDo> {
}
