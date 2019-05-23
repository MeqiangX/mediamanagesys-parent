package com.mingkai.mappermodule.model.Do.uc;


import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mappermodule.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-03-30 17:25
 */
@TableName("mesys_user")
@Data
public class UserDO extends BaseDo {

    private String userName;

    private String userPassword;

    private String email;

    private String phone;

    private Integer status;

}
