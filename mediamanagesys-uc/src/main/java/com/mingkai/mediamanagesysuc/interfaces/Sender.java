package com.mingkai.mediamanagesysuc.interfaces;

import com.mingkai.mediamanagesysuc.model.MailModel;
import com.mingkai.mediamanagesysuc.pojo.po.MessagePo;

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
