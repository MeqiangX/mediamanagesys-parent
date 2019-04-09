package com.mingkai.mediamanagesysuc.enums;

import lombok.Data;

/**
 * @description: 发送消息目的枚举
 * @author: Created by 云风 on 2019-03-31 20:14
 */
public enum MessageEnum {

    LOGIN("common_login","0"),

    LOGIN_PHONE("phone_login","1"),

    LOGIN_EMAIL("email_login","2"),

    REGISTER_PHONE("phone_register","3"),

    REGISTER_EMAIL("email_register","4");

    private String key;
    private String val;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    MessageEnum(String key, String val) {
        this.key = key;
        this.val = val;
    }

    public static MessageEnum getMessageEnum(String key){
        for (MessageEnum messageEnum:MessageEnum.values()){
            if (messageEnum.getKey().equals(key)){
                return messageEnum;
            }
        }
        return null;
    }
}
