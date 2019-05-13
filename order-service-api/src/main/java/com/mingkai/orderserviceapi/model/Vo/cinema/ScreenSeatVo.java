package com.mingkai.orderserviceapi.model.Vo.cinema;

import com.mingkai.orderserviceapi.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-30 14:02
 */
@Data
@ApiModel("坐席VO")
public class ScreenSeatVo extends BaseVo {

    @ApiModelProperty(name = "screenArrangeId",value = "排片id")
    private Integer screenArrangeId;

    @ApiModelProperty(name = "screeningHallX",value = "横排number")
    private Integer screeningHallX;

    @ApiModelProperty(name = "screeningHallY",value = "竖排number")
    private Integer screeningHallY;

    @ApiModelProperty(name = "status",value = "座位状态")
    private Integer status;

    @ApiModelProperty(name = "isPurchased",value = "是否被购买")
    private Integer isPurchased;

}
