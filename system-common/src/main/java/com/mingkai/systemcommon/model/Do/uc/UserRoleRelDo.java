package com.mingkai.systemcommon.model.Do.uc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.systemcommon.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-09 16:14
 */
@Data
@TableName("mesys_user_role")
public class UserRoleRelDo extends BaseDo {

    private Integer userId;

    private Integer roleId;

}
