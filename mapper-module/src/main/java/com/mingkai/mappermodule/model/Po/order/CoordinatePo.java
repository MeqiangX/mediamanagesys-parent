package com.mingkai.mappermodule.model.Po.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-02 22:27
 */
@Data
@ApiModel("坐席坐标and排片idPO")
public class CoordinatePo {

    @ApiModelProperty(name = "seats",value = "坐席坐标List")
    private List<SeatPo> seats;

    @ApiModelProperty(name = "arrangeId",value = "排片id")
    private Integer arrangeId;

}
