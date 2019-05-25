package com.mingkai.mediamanagesysmapper.model.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-04 21:13
 */
@Data
@ApiModel("图表VO")
public class EchartsVo {

    @ApiModelProperty(name = "date",value = "日期")
    private String date;

    @ApiModelProperty(name = "count",value = "数量")
    private Integer count;

}
