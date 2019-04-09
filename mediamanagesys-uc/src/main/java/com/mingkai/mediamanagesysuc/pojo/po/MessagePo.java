package com.mingkai.mediamanagesysuc.pojo.po;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: Created by 云风 on 2019-03-31 20:03
 */
@Api("发送短信的PO")
@Data
public class MessagePo {

    @ApiModelProperty(name = "receverPhone",value = "接收人手机",notes = "接收人手机",example = "11010101010")
    private String receverPhone;

    @ApiModelProperty(name = "code",value = "验证码",notes = "六位验证码",example = "110023",hidden = true)
    private String code;

    @ApiModelProperty(name = "sendType",value = "发送目的",notes = "发送目的(具体见MessageEnum)",example = "1")
    private String sendType;

}
