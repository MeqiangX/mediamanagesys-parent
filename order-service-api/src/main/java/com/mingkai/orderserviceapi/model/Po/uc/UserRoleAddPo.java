package com.mingkai.orderserviceapi.model.Po.uc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-09 15:54
 */
@Data
@ApiModel("用户添加角色Po")
public class UserRoleAddPo {

    @ApiModelProperty(name = "userId",value = "用户id",example = "1")
    private Integer userId;

    @ApiModelProperty(name = "roleIds",value = "角色id列表",notes = "{1,2,3}")
    private List<Integer> roleIds;

}
