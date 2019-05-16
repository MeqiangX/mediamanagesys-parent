package com.mingkai.ucserviceapi.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mappermodule.common.API;
import com.mingkai.mappermodule.model.Do.uc.UserDO;
import com.mingkai.mappermodule.model.Do.uc.UserInfoDo;
import com.mingkai.mappermodule.model.MailModel;
import com.mingkai.mappermodule.model.Po.uc.*;
import com.mingkai.ucserviceapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;


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

   @Autowired
   private HttpSession session;

    @ApiOperation("邮件发送接口")
    @PostMapping("/sendEmail")
    public R<Boolean> sendEmail(MailModel mailModel){

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
    public R<Boolean> sendMessage(MessagePo messagePo){
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


    @ApiOperation("得到验证码")
    @PostMapping("/getCode")
    public R<String> getCode(MessagePo messagePo){
        return new APITemplate<String>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected String process() throws BizException {
                return userService.getCode(messagePo);
            }
        }.execute();
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public R<Boolean> logout(Integer userId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userService.logout(userId);
            }
        }.execute();
    }


    @ApiOperation("登录")
    @PostMapping("/login")
    public R<UserDO> phoneLogin(@RequestBody LoginPo loginPo){
        return new APITemplate<UserDO>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {
                System.out.println("sessionId: " + session.getId());
            }

            @Override
            protected UserDO process() throws BizException {
                return userService.login(loginPo);
            }
        }.execute();
    }

    @ApiOperation("得到登录用户信息 from session")
    @GetMapping("/getloginUser-fromSession")
    public R<UserDO> getLoginUserFromSession(Integer userId){
        return new APITemplate<UserDO>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {
                System.out.println("sessionId: " + session.getId());
            }

            @Override
            protected UserDO process() throws BizException {
                return userService.getLoginUser(userId);
            }
        }.execute();
    }

    @ApiOperation("邮箱/手机注册")
    @PostMapping("/register")
    public R<Boolean> register(RegisterPo registerPo){
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
                return Objects.nonNull(userService.registedPhone(phone));
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
               return Objects.nonNull(userService.registedEmail(email));
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


    @ApiOperation("修改密码")
    @PostMapping("/update-pwd")
    public R<Boolean> updatePwd(PwdUpdatePo pwdUpdatePo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userService.updatePwd(pwdUpdatePo);
            }
        }.execute();
    }


    @ApiOperation("给用户赋予角色")
    @PostMapping("user-role-add")
   // @PreAuthorize("hasPermission('front_user_default','测试权限') or hasRole('main_admin')")
    public R<Boolean> addRoleToUser(UserRoleAddPo userRoleAddPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userService.addRoleToUser(userRoleAddPo);
            }
        }.execute();
    }


    @ApiOperation("用户列表")
    @GetMapping("users-page")
    public R<Page<UserDO>> userPages(UserPagePo userPagePo){

        return new APITemplate<Page<UserDO>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<UserDO> process() throws BizException {
                return userService.userPages(userPagePo);
            }
        }.execute();

    }


    @ApiOperation("通过id得到用户")
    @GetMapping("user-by-id")
    public R<UserDO> userById(Integer id){
        return new APITemplate<UserDO>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected UserDO process() throws BizException {
                return userService.userById(id);
            }
        }.execute();
    }

    @ApiOperation("通过用户id 取得 用户信息")
    @GetMapping("/userinfo-by-id")
    public R<UserInfoDo> userInfoById(Integer userId){
        return new APITemplate<UserInfoDo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected UserInfoDo process() throws BizException {
                return userService.userInfoById(userId);
            }
        }.execute();
    }


    @ApiOperation("修改用户信息")
    @PostMapping("/userinfo-update")
    public R<Boolean> updateUserInfo(@RequestBody UserInfoPo userInfoPo){

        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userService.updateUserInfo(userInfoPo);
            }
        }.execute();

    }




}
