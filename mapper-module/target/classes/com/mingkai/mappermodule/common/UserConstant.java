package com.mingkai.mappermodule.common;

import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-03-30 21:09
 */
@Data
public class UserConstant {

    public static String REGISTER_CODE = "register";

    public static String LOGIN_CODE = "login";

    /**
     * 手机登录
     */
    public static String LOGIN_PHONE = "phone";

    /**
     * 邮箱登录
     */
    public static String LOGIN_EMAIL = "email";

    /**
     * 普通登录
     */
    public static String LOGIN_COMMON = "common";

}
