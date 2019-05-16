package com.mingkai.mediamanagesysuc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mappermodule.model.Do.uc.UserDO;
import com.mingkai.mappermodule.model.Do.uc.UserInfoDo;
import com.mingkai.mappermodule.model.MailModel;
import com.mingkai.mappermodule.model.Po.uc.*;
import com.mingkai.mappermodule.utils.check.CheckOfR;
import com.mingkai.mediamanagesysuc.feigns.UserRpcFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-15 13:56
 */
@Service
public class UserRpcService {

    @Autowired
    private UserRpcFeign userRpcFeign;

    public Boolean sendEmail(MailModel mailModel){
        return CheckOfR.check(userRpcFeign.sendEmail(mailModel));
    }

    public Boolean sendMessage(MessagePo messagePo){
        return CheckOfR.check(userRpcFeign.sendMessage(messagePo));
    }

    public String getCode(MessagePo messagePo){
        return CheckOfR.check(userRpcFeign.getCode(messagePo));
    }


    public Boolean logout(Integer userId){
        return CheckOfR.check(userRpcFeign.logout(userId));
    }


    public UserDO phoneLogin(LoginPo loginPo){
        return CheckOfR.check(userRpcFeign.phoneLogin(loginPo));
    }


    public UserDO getLoginUserFromSession(Integer userId){
        return CheckOfR.check(userRpcFeign.getLoginUserFromSession(userId));
    }


    public Boolean register(RegisterPo registerPo){
        return CheckOfR.check(userRpcFeign.register(registerPo));
    }


    public Boolean phoneCheck(String phone){
        return CheckOfR.check(userRpcFeign.phoneCheck(phone));
    }


    public Boolean emailCheck(String email){
        return CheckOfR.check(userRpcFeign.emailCheck(email));
    }


    public Boolean userNameCheck(String userName){
        return CheckOfR.check(userRpcFeign.userNameCheck(userName));
    }


    public Boolean updatePwd(PwdUpdatePo pwdUpdatePo){
        return CheckOfR.check(userRpcFeign.updatePwd(pwdUpdatePo));
    }


    public Boolean addRoleToUser(UserRoleAddPo userRoleAddPo){
        return CheckOfR.check(userRpcFeign.addRoleToUser(userRoleAddPo));
    }


    public Page<UserDO> userPages(UserPagePo userPagePo){
        return CheckOfR.check(userRpcFeign.userPages(userPagePo));
    }


    public UserDO userById(Integer id){
        return CheckOfR.check(userRpcFeign.userById(id));
    }


    public UserInfoDo userInfoById(Integer userId){
        return CheckOfR.check(userRpcFeign.userInfoById(userId));
    }


    public Boolean updateUserInfo(UserInfoPo userInfoPo){
        return CheckOfR.check(userRpcFeign.updateUserInfo(userInfoPo));
    }

}
