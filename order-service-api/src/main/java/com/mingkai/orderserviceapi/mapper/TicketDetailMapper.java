package com.mingkai.orderserviceapi.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.orderserviceapi.model.Do.EchartsDo;
import com.mingkai.orderserviceapi.model.Do.order.TicketDetailDo;
import com.mingkai.orderserviceapi.model.Po.order.TicketSearchPo;
import com.mingkai.orderserviceapi.model.Vo.ticket.TicketDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 15:45
 */
@Repository
@Mapper
public interface TicketDetailMapper extends BaseMapper<TicketDetailDo> {

    Page<TicketDetailVo> orderSearch(@Param("ticketSearchPo") TicketSearchPo ticketSearchPo);

    List<EchartsDo> orderEchartsData(@Param("startYear") Integer startYear, @Param("startMonth") Integer startMonth
            , @Param("endYear") Integer endYear, @Param("endMonth") Integer endMonth);

}
