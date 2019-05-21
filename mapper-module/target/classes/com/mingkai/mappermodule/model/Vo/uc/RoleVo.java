package com.mingkai.mappermodule.model.Vo.uc;


import com.mingkai.mappermodule.model.Vo.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-08 14:06
 */
@Data
@ApiModel("返回的角色Vo")
public class RoleVo extends BaseVo {

    @ApiModelProperty(name = "roleName",value = "角色名称")
    private String roleName;

    @ApiModelProperty(name = "description",value = "描述")
    private String description;

}
