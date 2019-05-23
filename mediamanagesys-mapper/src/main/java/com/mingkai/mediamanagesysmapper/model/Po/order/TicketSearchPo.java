package com.mingkai.mediamanagesysmapper.model.Po.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-15 10:40
 */
@Data
@ApiModel("订单查询PO")
public class TicketSearchPo extends Page {

    // 用户id 用户名 创建时间 价格 电影id 电影名 影城id 影城名
    @ApiModelProperty(name = "userId",value = "用户id",example = "1")
    private Integer userId;

    @ApiModelProperty(name = "userName",value = "用户名",example = "用户名")
    private String userName;

    @ApiModelProperty(name = "createDate",value = "创建日期",example = "2019-04-11")
    private String createDate;

    @ApiModelProperty(name = "price",value = "价格",example = "22.5")
    private BigDecimal price;

    @ApiModelProperty(name = "movieId",value = "电影id",example = "1")
    private String movieId;

    @ApiModelProperty(name = "movieName",value = "电影名",example = "电影名")
    private String movieName;

    @ApiModelProperty(name = "cinemaId",value = "影院id",example = "1")
    private Integer cinemaId;

    @ApiModelProperty(name = "cinemaName",value = "影院名",example = "影院名")
    private String cinemaName;

    @ApiModelProperty(name = "orderColumn",value = "排序字段",example = "create_time")
    private String orderColumn;

    @ApiModelProperty(name = "orderType",value = "排序类型ASC/DESC",example = "DESC")
    private String orderType;



}
