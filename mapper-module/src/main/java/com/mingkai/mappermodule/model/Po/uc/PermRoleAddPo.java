package com.mingkai.mappermodule.model.Po.uc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-08 17:49
 */
@Data
@ApiModel("角色移除/添加权限PO")
public class PermRoleAddPo {

    @ApiModelProperty(name = "roleId",value = "角色id",example = "1")
    private Integer roleId;

    @ApiModelProperty(name = "permisionList",value = "权限列表",example = "{1,2,3}")
    private List<Integer> permisionList;

}
