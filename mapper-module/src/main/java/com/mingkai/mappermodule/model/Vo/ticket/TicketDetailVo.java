package com.mingkai.mappermodule.model.Vo.ticket;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-15 10:38
 */
@Data
@ApiModel("订单VO")
public class TicketDetailVo{

    private String orderId;

    private String createTime;

    private Integer cinemaId;

    private String cinemaName;

    private Integer screeningId;

    private String screeningHallName;

    private Integer screeningHallX;

    private Integer screeningHallY;

    private String movieId;

    private String timeScopeStart;

    private String movieName;

    private Integer userId;

    private String userName;

    private BigDecimal price;


}
