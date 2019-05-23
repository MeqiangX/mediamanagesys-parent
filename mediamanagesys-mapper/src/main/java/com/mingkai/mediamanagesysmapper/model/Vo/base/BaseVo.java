package com.mingkai.mediamanagesysmapper.model.Vo.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 20:15
 */
@Data
public class BaseVo {

    @ApiModelProperty(name = "id",value = "主键id")
    private Integer id;

    @ApiModelProperty(name = "createTime",value = "创建时间")
    private Date createTime;

    @ApiModelProperty(name = "updateTime",value = "跟新时间")
    private Date updateTime;

    @ApiModelProperty(name = "creator",value = "创建人")
    private String creator;

    @ApiModelProperty(name = "updator",value = "跟新人")
    private String updator;

    @ApiModelProperty(name = "isDeleted",value = "是否删除")
    private Integer isDeleted;

}
