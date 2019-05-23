package com.mingkai.mediamanagesysmapper.model.Po.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-06 17:24
 */
@Data
@ApiModel("放映厅查询PO")
public class ScreenPagePo extends Page {

    @ApiModelProperty("放映厅名称")
    private String screeningHallName;

    private Integer cinemaId;

}
