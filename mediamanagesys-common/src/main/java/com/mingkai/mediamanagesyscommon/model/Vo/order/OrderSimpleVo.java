package com.mingkai.mediamanagesyscommon.model.Vo.order;

import com.mingkai.mediamanagesyscommon.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-30 21:08
 */
@Data
@ApiModel("支付界面的订单VO")
public class OrderSimpleVo extends BaseVo {

    private String orderId;

    private Integer userId;

    private String seatIds;

    private BigDecimal price;

    private Integer status;

    private String movieName;

    private String movieImage;

    private String timeScopeStart;

    private String cinemaName;

    private String cinemaAddr;

    private String cinemaPhone;

    private String screeningHallName;

    private List<String> seats;

}
