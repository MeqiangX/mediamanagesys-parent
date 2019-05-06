package com.mingkai.mediamanagesysbackend.model.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 17:57
 */
@ApiModel("放映厅添加PO")
@Data
public class ScreenRoomAddPo {

    @ApiModelProperty(name = "id",value = "id")
    private Integer id;

    @ApiModelProperty(name="screeningHallName",value = "放映厅名称")
    private String screeningHallName;

    @ApiModelProperty(name="screeningHallX",value = "放映厅排数")
    private Integer screeningHallX;

    @ApiModelProperty(name = "screeningHallY",value = "放映厅列数")
    private Integer screeningHallY;

}
