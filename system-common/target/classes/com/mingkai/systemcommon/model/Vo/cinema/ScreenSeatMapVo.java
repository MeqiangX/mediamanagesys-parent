package com.mingkai.systemcommon.model.Vo.cinema;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-30 16:11
 */
@Data
@ApiModel("坐席VO")
public class ScreenSeatMapVo {

    @ApiModelProperty(name = "screeningHallX",value = "横排row编号")
    private Integer screeningHallX;

    @ApiModelProperty(name = "seatRowList",value = "row横排座位列表")
    private List<ScreenSeatVo> seatRowList;

}
