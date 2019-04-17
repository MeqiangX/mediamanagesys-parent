package com.mingkai.mediamanagesyscommon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesyscommon.model.Do.order.TicketDetailDo;
import com.mingkai.mediamanagesyscommon.model.Po.order.TicketSearchPo;
import com.mingkai.mediamanagesyscommon.model.Vo.ticket.TicketDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 15:45
 */
@Repository
@Mapper
public interface TicketDetailMapper extends BaseMapper<TicketDetailDo> {

    Page<TicketDetailVo> orderSearch(@Param("ticketSearchPo")TicketSearchPo ticketSearchPo);

}
