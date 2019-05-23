package com.mingkai.systemcommon.model.Po.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-02 22:29
 */
@Data
@ApiModel("坐席PO")
public class SeatPo {

    @ApiModelProperty(name = "row",value = "横排")
    private Integer row;

    @ApiModelProperty(name = "col",value = "竖排")
    private Integer col;

}
