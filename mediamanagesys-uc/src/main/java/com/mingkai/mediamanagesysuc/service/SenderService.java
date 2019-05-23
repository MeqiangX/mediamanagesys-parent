package com.mingkai.mediamanagesysuc.service;


import com.aliyun.oss.ClientException;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.dtstack.plat.lang.exception.BizException;
import com.mingkai.mediamanagesysmapper.utils.redis.RedisUtil;
import com.mingkai.mediamanagesysuc.commonUtil.CodeUtil;
import com.mingkai.mediamanagesysuc.interfaces.Sender;
import com.mingkai.mediamanagesysuc.model.MailModel;
import com.mingkai.mediamanagesysuc.pojo.po.MessagePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @description: SenderService
 * @author: Created by 云风 on 2019-03-31 15:23
 */
@Service
@Slf4j
public class SenderService implements Sender {

    @Autowired
    private JavaMailSender javaMailSender;


    @Autowired
    private RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String MAIL_SENDER;

    /**
     * 发送html样式的邮件 点对点
     * @param mailModel
     * @return
     */
    @Override
    public Boolean sendMail(MailModel mailModel) {

        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            messageHelper.setFrom(MAIL_SENDER);
            messageHelper.setTo(mailModel.getRevever());
            messageHelper.setSubject(mailModel.getSubject());
            messageHelper.setText(mailModel.getContent(),true);

            //发送
            javaMailSender.send(message);
        }catch (Exception e){
            log.info("邮件发送失败",e);
            throw new BizException("邮件发送失败");
        }

        return true;
    }

    /**
     * 发送短信验证码
     * @return
     */
    @Override
    public Boolean sendMessage(MessagePo messagePo) {
        messagePo.setCode(CodeUtil.getRandom());
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI7suwceOqdho7", "1uCBXdyNUTWymjF2E8RuNFu4B5o1hD");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", messagePo.getReceverPhone());
        request.putQueryParameter("SignName", "明凯电影院");
        request.putQueryParameter("TemplateCode", "SMS_162522237");
        request.putQueryParameter("TemplateParam", "{\"code\":"+ messagePo.getCode() + "}");

        try {
            CommonResponse response = client.getCommonResponse(request);

        } catch (ServerException e) {
           log.info(String.format("短信发送失败-{}",e.getMessage()));
            throw new BizException(e.getMessage());
        } catch (ClientException e) {
           log.info(String.format("短信发送失败-{}",e.getMessage()));
            throw new BizException(e.getMessage());
        }  catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
        }

        return true;
    }
}
