package com.mingkai.ucserviceapi.interfaces;


import com.mingkai.mappermodule.model.MailModel;
import com.mingkai.mappermodule.model.Po.uc.MessagePo;

/**
 * @description:  发送抽象接口
 * @author: Created by 云风 on 2019-03-31 10:16
 */
public interface Sender {

    /**
     * 发送邮件
     * @param mailModel
     * @return
     */
    Boolean sendMail(MailModel mailModel);

    /**
     * 发送短信
     * @return
     */
    Boolean sendMessage(MessagePo messagePo);

}
