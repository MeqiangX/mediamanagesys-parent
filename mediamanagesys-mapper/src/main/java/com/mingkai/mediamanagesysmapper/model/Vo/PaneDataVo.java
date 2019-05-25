package com.mingkai.mediamanagesysmapper.model.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-04 20:03
 */
@Data
@ApiModel("欢迎页面板返回VO")
public class PaneDataVo implements Serializable {

    @ApiModelProperty(name = "name",value = "数据项名称")
    private String name;

    @ApiModelProperty(name = "count",value = "累计总数")
    private Integer count;

    public PaneDataVo() {
    }

    public PaneDataVo(String name, Integer count) {
        this.name = name;
        this.count = count;
    }
}

