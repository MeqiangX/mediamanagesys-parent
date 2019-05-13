package com.mingkai.orderserviceapi.model.Do;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.orderserviceapi.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-04 20:18
 */
@Data
@TableName("mesys_user_admin")
public class UserAdminDo extends BaseDo {

    private String userName;

    private String userPassword;

    private Integer status;

    private Integer parent;

}
