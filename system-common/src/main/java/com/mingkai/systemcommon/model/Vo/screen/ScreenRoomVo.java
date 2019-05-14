package com.mingkai.systemcommon.model.Vo.screen;


import com.mingkai.systemcommon.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 20:14
 */
@ApiModel("放映厅VO")
@Data
public class ScreenRoomVo extends BaseVo {

    @ApiModelProperty(name = "screeningHallName",value = "放映厅名称")
    private String screeningHallName;

    @ApiModelProperty(name = "screeningHallX",value = "放映厅行数")
    private Integer screeningHallX;

    @ApiModelProperty(name = "screeningHallY",value = "放映厅列数")
    private Integer screeningHallY;

}
