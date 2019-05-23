package com.mingkai.mappermodule.model.Do.uc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.mingkai.mappermodule.model.Do.base.BaseDo;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-07 14:03
 */
@Data
@TableName("mesys_user_info")
public class UserInfoDo extends BaseDo {

    private Integer userId;

    private String nicoName;

    private String birthday;

    private Integer sex;

    private String hobbies;

    private String signature;
}
