package com.mingkai.mediamanagesysuc.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 邮件发送Model
 * @author: Created by 云风 on 2019-03-30 21:03
 */
@Data
@Api("邮件发送的modelPo")
public class MailModel implements Serializable {

    /**
     * 邮件接收方
     */
    @ApiModelProperty(name = "recever",value = "接收人")
    private String revever;

    /**
     * 主题
     */
    @ApiModelProperty(hidden = true)
    private String subject;

    /**
     * 内容主体
     */
    @ApiModelProperty(hidden = true)
    private String content;

    /**
     * 发送目的
     */
    @ApiModelProperty(name = "sendType",value = "发送目的",notes = "发送目的(具体见MessageEnum)",example = "1")
    private String sendType;

    /**
     * code
     */
    @ApiModelProperty(name = "code",value = "验证码",hidden = true)
    private String code;
}
