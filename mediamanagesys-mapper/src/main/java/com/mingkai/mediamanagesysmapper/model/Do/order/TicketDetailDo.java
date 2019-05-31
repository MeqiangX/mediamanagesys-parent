package com.mingkai.mediamanagesysmapper.model.Do.order;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesysmapper.model.Do.base.BaseDo;
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

    private String seatIds;

    private BigDecimal price;

    private Integer status;

    private String movie;

    private Integer cinemaId;

    private String cinema;

    private String screen;

    private String seats;

    private String timeScopeStart;

}
