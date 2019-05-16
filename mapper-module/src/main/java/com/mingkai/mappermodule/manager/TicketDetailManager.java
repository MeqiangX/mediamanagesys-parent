package com.mingkai.mappermodule.manager;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingkai.mappermodule.mapper.TicketDetailMapper;
import com.mingkai.mappermodule.model.Do.order.TicketDetailDo;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 15:46
 */
@Repository
public class TicketDetailManager extends ServiceImpl<TicketDetailMapper, TicketDetailDo> {
}
