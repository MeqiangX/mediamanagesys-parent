package com.mingkai.mediamanagesysuc.pojo.po;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-03-31 20:27
 */
@Api("登录PO")
@Data
public class LoginPo {

    @ApiModelProperty(name = "loginOption",value = "登录方式",notes = "1(手机)具体见MessageEnum",example = "1")
    private String loginOption;

    @ApiModelProperty(name = "account",value = "账号",notes = "手机号、邮箱等",example = "16231651@qq.com")
    private String account;

    @ApiModelProperty(name = "code",value = "验证码",notes = "验证码",example = "112233")
    private String code;

    @ApiModelProperty(name = "password",value = "密码",notes = "密码",example = "123456")
    private String password;
}
