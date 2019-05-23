package com.mingkai.mediamanagesysmapper.model.Po.uc;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-09 12:58
 */
@Data
@ApiModel("用户信息更新PO")
public class UserInfoPo {

    private Integer userId;

    private String nicoName;

    private String birthday;

    private Integer sex;

    private String hobbies;

    private String signature;

}
