package com.mingkai.mediamanagesyscommon.model.Do.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesyscommon.model.Do.base.BaseDo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 15:44
 */
@Data
@TableName("mesys_ticket_detail")
public class TicketDetailDo extends BaseDo {

    private String orderId;

    private Integer userId;

    private Integer seatId;

    private BigDecimal price;

}
