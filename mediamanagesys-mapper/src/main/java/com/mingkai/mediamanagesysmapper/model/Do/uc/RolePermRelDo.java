package com.mingkai.mediamanagesysmapper.model.Do.uc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mediamanagesysmapper.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description: 权限角色关联表
 * @author: Created by 云风 on 2019-04-08 15:25
 */
@Data
@TableName("mesys_role_permision")
public class RolePermRelDo extends BaseDo {

    private Integer roleId;

    private Integer permisionId;

}
