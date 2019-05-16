package com.mingkai.mediamanagesysuc.feigns;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.web.R;
import com.mingkai.mappermodule.common.API;
import com.mingkai.mappermodule.model.Do.UserAdminDo;
import com.mingkai.mappermodule.model.Do.uc.UserDO;
import com.mingkai.mappermodule.model.Do.uc.UserInfoDo;
import com.mingkai.mappermodule.model.MailModel;
import com.mingkai.mappermodule.model.Po.uc.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-15 13:39
 */
@FeignClient(value = "uc-service-api")
@Repository
public interface UserRpcFeign {

    @RequestMapping(value = API.API_USER +  "/sendEmail",method = RequestMethod.POST)
    R<Boolean> sendEmail(@RequestBody MailModel mailModel);

    @RequestMapping(value = API.API_USER +  "/sendMessage",method = RequestMethod.POST)
    R<Boolean> sendMessage(@RequestBody MessagePo messagePo);

    @RequestMapping(value = API.API_USER +  "/getCode",method = RequestMethod.POST)
    R<String> getCode(@RequestBody MessagePo messagePo);

    @RequestMapping(value = API.API_USER +  "/logout",method = RequestMethod.GET)
    R<Boolean> logout(@RequestParam("userId") Integer userId);


    @RequestMapping(value = API.API_USER + "/login",method = RequestMethod.POST)
    R<UserDO> phoneLogin(@RequestBody LoginPo loginPo);


    @RequestMapping(value = API.API_USER + "/getloginUser-fromSession",method = RequestMethod.GET)
    R<UserDO> getLoginUserFromSession(@RequestParam("userId") Integer userId);

    @RequestMapping(value = API.API_USER + "/register",method = RequestMethod.POST)
    R<Boolean> register(@RequestBody RegisterPo registerPo);

    @RequestMapping(value = API.API_USER + "/phone-check/{phone}")
    R<Boolean> phoneCheck(@PathVariable String phone);

    @RequestMapping(value = API.API_USER + "/email-check/{email}")
    R<Boolean> emailCheck(@PathVariable String email);

    @RequestMapping(value = API.API_USER + "/username-check/{userName}")
    R<Boolean> userNameCheck(@PathVariable String userName);


    @RequestMapping(value = API.API_USER + "/update-pwd",method = RequestMethod.POST)
    R<Boolean> updatePwd(@RequestBody PwdUpdatePo pwdUpdatePo);


    @RequestMapping(value = API.API_USER + "/user-role-add",method = RequestMethod.POST)
    // @PreAuthorize("hasPermission('front_user_default','测试权限') or hasRole('main_admin')")
    R<Boolean> addRoleToUser(@RequestBody UserRoleAddPo userRoleAddPo);

    @RequestMapping(value = API.API_USER + "/users-page",method = RequestMethod.POST)
    R<Page<UserDO>> userPages(@RequestBody UserPagePo userPagePo);


    @RequestMapping(value = API.API_USER + "/user-by-id",method = RequestMethod.GET)
    R<UserDO> userById(@RequestParam("id") Integer id);


    @RequestMapping(value = API.API_USER + "/userinfo-by-id",method = RequestMethod.GET)
    R<UserInfoDo> userInfoById(@RequestParam("userId") Integer userId);


    @RequestMapping(value = API.API_USER + "/userinfo-update",method = RequestMethod.POST)
    R<Boolean> updateUserInfo(@RequestBody UserInfoPo userInfoPo);




    // -------------管理员

    @ApiOperation("后台登录")
    @RequestMapping(value = API.API_USER_ADMIN + "/admin-login",method = RequestMethod.POST)
    R<UserAdminDo> adminLogin(@RequestBody UserAdminLoginPo userAdminLoginPo);


    @ApiOperation("增加/修改管理员信息")
    @RequestMapping(value = API.API_USER_ADMIN + "/add-update-admin",method = RequestMethod.POST)
    R<Boolean> addUpdateAdmin(@RequestBody UserAdminAddPo userAdminAddPo);

    @ApiOperation("删除管理员")
    @RequestMapping(value = API.API_USER_ADMIN + "/delete-admin",method = RequestMethod.GET)
    R<Boolean> deleteAdmin(@RequestParam("id") Integer id);

    @ApiOperation("查询下级管理员")
    @RequestMapping(value = API.API_USER_ADMIN + "/admins-under",method = RequestMethod.POST)
    R<Page<UserAdminDo>> adminsUnder(@RequestBody UserAdminPagePo userAdminPagePo);


    @ApiOperation("查询管理员by id")
    @RequestMapping(value = API.API_USER_ADMIN + "/admin-by-id",method = RequestMethod.GET)
    R<UserAdminDo> adminById(@RequestParam("id") Integer id);


    @ApiOperation("添加/修改用户")
    @RequestMapping(value = API.API_USER_ADMIN + "/add-update-user",method = RequestMethod.POST)
    R<Boolean> addOrUpdateUser(@RequestBody UserAddPo userAddPo);

    @ApiOperation("删除用户")
    @RequestMapping(value = API.API_USER_ADMIN + "/delete-user-by-id",method = RequestMethod.GET)
    R<Boolean> deleteUserById(@RequestParam("id") Integer id);

    @ApiOperation("修改密码-管理员")
    @RequestMapping(value = API.API_USER_ADMIN + "/update-pwd",method = RequestMethod.GET)
    R<Boolean> updatePwd(@RequestParam("id") Integer id,
                         @RequestParam("oldPwd") String oldPwd,
                         @RequestParam("newPwd") String newPwd);



}
