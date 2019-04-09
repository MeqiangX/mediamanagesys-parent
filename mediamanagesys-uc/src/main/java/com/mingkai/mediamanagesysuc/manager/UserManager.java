package com.mingkai.mediamanagesysuc.manager;

import com.dtstack.plat.lang.exception.BizException;
import com.mingkai.mediamanagesysuc.commonUtil.RedisUtil;
import com.mingkai.mediamanagesysuc.constant.time.TimeConstant;
import com.mingkai.mediamanagesysuc.mapper.UserMapper;
import com.mingkai.mediamanagesysuc.model.MailModel;
import com.mingkai.mediamanagesysuc.pojo.po.MessagePo;
import com.mingkai.mediamanagesysuc.service.SenderService;
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
