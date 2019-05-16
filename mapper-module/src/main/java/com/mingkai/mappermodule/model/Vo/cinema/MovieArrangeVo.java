package com.mingkai.mappermodule.model.Vo.cinema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-24 14:47
 */
@Data
@ApiModel("排片Vo")
public class MovieArrangeVo {

    @ApiModelProperty(name = "id",value = "id",example = "1")
    private Integer id;

    @ApiModelProperty(name = "cinemaScreenId",value = "放映厅+影院id")
    private Integer cinemaScreenId;

    @ApiModelProperty(name = "screeningHallName",value = "放映厅名称")
    private String screeningHallName;

    @ApiModelProperty(name = "arrangeDate",value = "放映日期")
    private String arrangeDate;

    @ApiModelProperty(name = "timeScopeStart",value = "开始时间")
    private String timeScopeStart;

    @ApiModelProperty(name = "timeScopeEnd",value = "散场时间")
    private String timeScopeEnd;

    @ApiModelProperty(name = "price",value = "价格")
    private BigDecimal price;

    @ApiModelProperty(name = "language",value = "语言版本")
    private String language;

}
