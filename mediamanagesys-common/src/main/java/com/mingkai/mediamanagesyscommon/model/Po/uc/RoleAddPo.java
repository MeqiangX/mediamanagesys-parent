package com.mingkai.mediamanagesyscommon.model.Po.uc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-08 20:52
 */
@Data
@ApiModel("添加角色Po")
public class RoleAddPo {

    @ApiModelProperty(name = "roleName",value = "角色名")
    private String roleName;

    @ApiModelProperty(name = "description",value = "描述")
    private String description;

}
