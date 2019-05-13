package com.mingkai.orderserviceapi.model.Vo.cinema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-07 22:08
 */
@Data
@ApiModel("后台排片记录VO")
public class MovieArgVo {

    private Integer id;

    private String cinemaName;

    private String screeningHallName;

    private String movieId;

    private String movieName;

    private String arrangeDate;

    private String timeScopeStart;

    private BigDecimal price;

    private String language;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    private Date createTime;

    @ApiModelProperty(name = "updateTime",value = "跟新时间")
    private Date updateTime;

}
