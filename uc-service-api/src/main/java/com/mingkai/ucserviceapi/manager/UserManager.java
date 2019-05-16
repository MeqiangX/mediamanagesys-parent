package com.mingkai.ucserviceapi.manager;


import com.mingkai.mappermodule.common.TimeConstant;
import com.mingkai.mappermodule.model.MailModel;
import com.mingkai.mappermodule.model.Po.uc.MessagePo;
import com.mingkai.mappermodule.utils.redis.RedisUtil;
import com.mingkai.ucserviceapi.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Created by 云风 on 2019-03-31 20:46
 */
@Component
public class UserManager {

    @Autowired
    private SenderService senderService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 发送邮件之后 加入缓存
     * @param mailModel
     * @return
     */
    public Boolean sendMail(MailModel mailModel) {
        if (true == senderService.sendMail(mailModel)){
            //加入 redis
            return redisUtil.set(mailModel.getSendType()+"+"+mailModel.getRevever(),mailModel.getCode(), TimeConstant.EXPIRE_TIME_EMAIL);
        }
        return false;
    }


    /**
     * 发送短信验证码
     * @param messagePo
     * @return
     */
    public Boolean sendMessage(MessagePo messagePo) {
        if (true == senderService.sendMessage(messagePo)){
            //发送成功后 放入redis  登录 / 注册
            //将手机号和 发送目的 组成key 放入redis
            return redisUtil.set(messagePo.getSendType()+"+"+messagePo.getReceverPhone(),messagePo.getCode(), TimeConstant.EXPIRE_Time);
        }
        return false;
    }
}
