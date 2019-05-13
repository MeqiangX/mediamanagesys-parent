package com.mingkai.orderserviceapi.model.Po.cinema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 10:56
 */
@Data
@ApiModel("影院配置放映厅PO")
public class CinemaScreenUpdatePo {

    @ApiModelProperty(name = "cinemaId",value = "影院id",example = "1")
    private Integer cinemaId;

    @ApiModelProperty(name = "screenHallIdList",value = "跟新的放映厅配置列表",example = "[1,2]")
    private List<Integer> screenHallIdList;

    @ApiModelProperty(name = "option",value = "选项0 add 1 delete",example = "0")
    private Integer option;
}
