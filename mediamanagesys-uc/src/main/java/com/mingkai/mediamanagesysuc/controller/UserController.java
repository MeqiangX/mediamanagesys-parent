package com.mingkai.mediamanagesysuc.controller;


import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.common.API;
import com.mingkai.mediamanagesysuc.model.MailModel;
import com.mingkai.mediamanagesysuc.pojo.po.LoginPo;
import com.mingkai.mediamanagesysuc.pojo.po.MessagePo;
import com.mingkai.mediamanagesysuc.pojo.po.RegisterPo;
import com.mingkai.mediamanagesysuc.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @description:
 * @author: Created by 云风 on 2019-03-30 17:50
 */
@Api(tags = API.USER)
@RestController
@RequestMapping(API.API_USER)
public class UserController {

   @Autowired
   private UserService userService;

    @ApiOperation("邮件发送接口")
    @PostMapping("/sendEmail")
    public R<Boolean> sendEmail(@RequestBody MailModel mailModel){

        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userService.sendEmail(mailModel);
            }
        }.execute();

    }


    @ApiOperation("短信发送接口")
    @PostMapping("/sendMessage")
    public R<Boolean> sendMessage(@RequestBody MessagePo messagePo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userService.sendMessage(messagePo);
            }
        }.execute();
    }


    @ApiOperation("登录")
    @PostMapping("/login")
    public R<Boolean> phoneLogin(@RequestBody LoginPo loginPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userService.login(loginPo);
            }
        }.execute();
    }


    @ApiOperation("邮箱/手机注册")
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody RegisterPo registerPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userService.register(registerPo);
            }
        }.execute();
    }

    @ApiOperation("注册手机验证")
    @GetMapping("/phone-check/{phone}")
    public R<Boolean> phoneCheck(@PathVariable String phone){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userService.registedPhone(phone);
            }
        }.execute();
    }


    @ApiOperation("注册邮箱验证")
    @GetMapping("/email-check/{email}")
    public R<Boolean> emailCheck(@PathVariable String email){
       return new APITemplate<Boolean>() {
           @Override
           protected void checkParams() throws IllegalArgumentException {

           }

           @Override
           protected Boolean process() throws BizException {
               return userService.registedEmail(email);
           }
       }.execute();
    }

    @ApiOperation("重名校验")
    @GetMapping("username-check/{userName}")
    public R<Boolean> userNameCheck(@PathVariable String userName){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userService.repeatName(userName);
            }
        }.execute();
    }
}
