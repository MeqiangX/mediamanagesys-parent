package com.mingkai.mediamanagesysmapper.model.Po.uc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-08 15:54
 */
@Data
@ApiModel("查询角色的权限")
public class PermisionPagePo extends Page {

    @ApiModelProperty(name = "roleId",value = "角色id",example = "1")
    private Integer roleId;

}
