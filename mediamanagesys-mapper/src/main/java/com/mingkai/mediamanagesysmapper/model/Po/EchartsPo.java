package com.mingkai.mediamanagesysmapper.model.Po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-04 21:38
 */
@Data
@ApiModel("图表PO")
public class EchartsPo {

    @ApiModelProperty(name = "startYear",value = "开始年份")
    private Integer startYear;

    @ApiModelProperty(name = "startMonth",value = "开始月份")
    private Integer startMonth;

    @ApiModelProperty(name = "endYear",value = "结束年份")
    private Integer endYear;

    @ApiModelProperty(name = "endMonth",value = "结束月份")
    private Integer endMonth;

    @ApiModelProperty(name = "startDate",value = "开始日期")
    private String startDate;

    @ApiModelProperty(name = "endDate",value = "结束日期")
    private String endDate;

    @ApiModelProperty(name = "option",value = "选项(0 电影 1 影院 2 用户 3 订单)")
    private Integer option;

}
