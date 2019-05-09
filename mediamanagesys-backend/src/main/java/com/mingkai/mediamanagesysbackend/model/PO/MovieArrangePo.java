package com.mingkai.mediamanagesysbackend.model.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 21:16
 */
@ApiModel("排片PO")
@Data
public class MovieArrangePo {

    private Integer id;

    @ApiModelProperty(name = "cinemaId",value = "影院id",example = "1")
    private Integer cinemaId;

    @ApiModelProperty(name = "screenHallId",value = "放映厅id",example = "1")
    private Integer screenHallId;

    @ApiModelProperty(name = "cinemaScreenId",value = "影院放映厅relId",example = "1",hidden = true)
    private Integer cinemaScreenId;

    @ApiModelProperty(name = "date",value = "日期")
    private String date;

    @ApiModelProperty(name = "startTime",value = "开始时间")
    private String startTime;

    @ApiModelProperty(name = "movieId",value = "放映电影id")
    private String movieId;

    @ApiModelProperty(name = "language",value = "语言版本")
    private String language;

    @ApiModelProperty(name = "price",value = "票价")
    private BigDecimal price;

}
