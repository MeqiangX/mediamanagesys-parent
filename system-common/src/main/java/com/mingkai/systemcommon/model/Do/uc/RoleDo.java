package com.mingkai.systemcommon.model.Do.uc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.systemcommon.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description: 角色DO
 * @author: Created by 云风 on 2019-04-05 11:40
 */
@Data
@TableName("mesys_role")
public class RoleDo extends BaseDo {

    private String roleName;

    private String description;

}
