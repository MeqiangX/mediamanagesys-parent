package com.mingkai.orderserviceapi.model.Po.uc;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-07 16:05
 */
@Data
@ApiModel("后台登录PO")
public class UserAdminLoginPo {

    private String userName;

    private String userPassword;

}
