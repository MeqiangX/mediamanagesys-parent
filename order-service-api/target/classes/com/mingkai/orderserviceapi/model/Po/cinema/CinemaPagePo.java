package com.mingkai.orderserviceapi.model.Po.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 9:23
 */
@Data
@ApiModel("影院选择PO")
public class CinemaPagePo extends Page {

    @ApiModelProperty(name = "movieId",value = "电影id",required = true)
    private String movieId;

    @ApiModelProperty(name = "areaId",value = "地区id",example = "1")
    private Integer areaId;

}
