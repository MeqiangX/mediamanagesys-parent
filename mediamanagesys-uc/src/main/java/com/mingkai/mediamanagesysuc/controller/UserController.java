package com.mingkai.mediamanagesysuc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.common.API;
import com.mingkai.mediamanagesyscommon.mapper.UserMapper;
import com.mingkai.mediamanagesyscommon.model.Do.uc.UserDO;
import com.mingkai.mediamanagesysuc.model.MailModel;
import com.mingkai.mediamanagesysuc.pojo.po.LoginPo;
import com.mingkai.mediamanagesysuc.pojo.po.MessagePo;
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
   private UserMapper userMapper;

   @Autowired
   private UserService userService;

    @ApiOperation("测试接口")
    @GetMapping("test")
    public UserDO get(){
        return userMapper.selectById(1);
    }


    @ApiOperation("测试模板接口")
    @GetMapping("test-template")
    public R<String> testTemplate(){
        return new APITemplate<String>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {
                throw  new BizException("BIZ错误模板");
            }

            @Override
            protected String process() throws BizException {

                return "模板调用成功";
            }
        }.execute();
    }



    @ApiOperation("测试登录接口")
    @GetMapping("/get/{account}/{password}")
    public R<UserDO> getByAccountAndPassword(@PathVariable String account,@PathVariable String password){

        return new APITemplate<UserDO>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected UserDO process() throws BizException {
                UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>()
                        .eq("user_password", password)
                        .and(e -> e.eq("phone",account).or().eq("email",account)));

                return userDO;
            }
        }.execute();

    }

    @ApiOperation("测试邮件发送接口")
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


    @ApiOperation("测试短信发送接口")
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


    @ApiOperation("手机验证码登录")
    @PostMapping("/phone-login")
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


}
