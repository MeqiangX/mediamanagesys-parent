package com.mingkai.mappermodule.model.Po.uc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-09 11:31
 */
@Data
@ApiModel("注册Po")
public class RegisterPo {

    @ApiModelProperty(name = "registerOption",value = "注册方式",notes = "3(手机)具体见MessageEnum",example = "3")
    private String registerOption;

    @ApiModelProperty(name = "userName",value = "用户名")
    private String userName;

    @ApiModelProperty(name = "password",value = "密码",notes = "密码",example = "123456")
    private String password;

    @ApiModelProperty(name = "phone",value = "手机号")
    private String phone;

    @ApiModelProperty(name = "email",value = "邮箱")
    private String email;

    @ApiModelProperty(name = "code",value = "验证码",notes = "验证码",example = "112233")
    private String code;

}
