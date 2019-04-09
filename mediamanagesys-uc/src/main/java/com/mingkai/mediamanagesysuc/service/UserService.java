package com.mingkai.mediamanagesysuc.service;

import com.dtstack.plat.lang.exception.BizException;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.mingkai.mediamanagesyscommon.mapper.UserMapper;
import com.mingkai.mediamanagesyscommon.model.Do.uc.UserDO;
import com.mingkai.mediamanagesysuc.commonUtil.CodeUtil;
import com.mingkai.mediamanagesysuc.commonUtil.RedisUtil;
import com.mingkai.mediamanagesysuc.enums.MessageEnum;
import com.mingkai.mediamanagesysuc.manager.UserManager;
import com.mingkai.mediamanagesysuc.model.MailModel;
import com.mingkai.mediamanagesysuc.pojo.po.LoginPo;
import com.mingkai.mediamanagesysuc.pojo.po.MessagePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @description:
 * @author: Created by 云风 on 2019-03-30 21:02
 */
@Service
public class UserService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ThymeleafService thymeleafService;
    /**
     * 登录service
     * @param loginPo
     * @return
     */
    public Boolean login(LoginPo loginPo){

        /**
         * 正常登录 登录成功之后的信息放入session 中
         */
        if (loginPo.getLoginOption().equals(MessageEnum.LOGIN.getVal())){
            Optional<UserDO> userDOOptional = Optional.fromNullable(userMapper.login(loginPo.getAccount(), loginPo.getPassword()));

            if (userDOOptional.isPresent()){
                //如果登录成功 则非空
                return true;
            }

            throw new BizException("用户名或密码错误");

        }
        if (loginPo.getLoginOption().equals(MessageEnum.LOGIN_PHONE.getVal())){
            // 手机验证码登录

            //对比当前手机号 和 验证码

            Object code = redisUtil.get(MessageEnum.LOGIN_PHONE.getVal() + "+" + loginPo.getAccount());

            Optional<Object> codeOptional = Optional.fromNullable(code);

            if (codeOptional.isPresent()){
                // 非空 和当前输入的验证码 比较
                if (codeOptional.get().toString().equals(loginPo.getCode())){
                    return true;
                }
            }

            throw new BizException("验证码错误");
        }
        if (loginPo.getLoginOption().equals(MessageEnum.LOGIN_EMAIL.getVal())){
            // 邮箱登录

            return false;

        }

        throw new BizException("错误代码");
    }

    /**
     * 发送邮箱验证码
     * @param mailModel
     * @return
     */
    public Boolean sendEmail(MailModel mailModel){

        Map<String,Object> params = Maps.newHashMap();

        String code = CodeUtil.getRandom();
        mailModel.setCode(code);
        if (mailModel.getSendType().equals(MessageEnum.LOGIN_EMAIL.getVal())){
            //邮箱登录
            params.put("title","明凯电影院系统-邮箱登录");
            params.put("type","邮箱登录");
            params.put("code",code);
        }

        if (mailModel.getSendType().equals(MessageEnum.REGISTER_EMAIL.getVal())){
            //邮箱注册

            params.put("title","明凯电影院系统-邮箱注册");
            params.put("type","邮箱注册");
            params.put("code",code);
        }

        mailModel.setSubject(params.get("title").toString());
        mailModel.setContent(thymeleafService.render("code/Code",params));

        return userManager.sendMail(mailModel);
    }

    public Boolean sendMessage(MessagePo messagePo){
        return userManager.sendMessage(messagePo);
    }
}
