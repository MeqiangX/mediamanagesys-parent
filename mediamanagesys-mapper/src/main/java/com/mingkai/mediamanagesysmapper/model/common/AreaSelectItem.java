package com.mingkai.mediamanagesysmapper.model.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-23 11:33
 */
@Data
@ApiModel("地域下拉框VO")
public class AreaSelectItem {

    @ApiModelProperty(name = "initials",value = "首字母")
    private String initials;

    @ApiModelProperty(name = "areas",value = "地区列表")
    private List<Area> areas;
}
