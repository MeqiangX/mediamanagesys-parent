package com.mingkai.mappermodule.model.Po.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-23 16:52
 */
@Data
@ApiModel("影院搜索PO")
public class CinemaSearchPo extends Page {

    @ApiModelProperty(name = "areaId",value = "地域id")
    private String areaId;

    @ApiModelProperty(name = "search",value = "影院名")
    private String search;

}
