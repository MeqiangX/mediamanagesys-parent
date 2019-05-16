package com.mingkai.ucserviceapi.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mappermodule.common.API;
import com.mingkai.mappermodule.model.Do.UserAdminDo;
import com.mingkai.mappermodule.model.Po.uc.UserAddPo;
import com.mingkai.mappermodule.model.Po.uc.UserAdminAddPo;
import com.mingkai.mappermodule.model.Po.uc.UserAdminLoginPo;
import com.mingkai.mappermodule.model.Po.uc.UserAdminPagePo;
import com.mingkai.ucserviceapi.service.UserAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-07 9:40
 */
@RestController
@Api(tags = API.USER_ADMIN)
@RequestMapping(API.API_USER_ADMIN)
public class UserAdminController {

    @Autowired
    private UserAdminService userAdminService;


    @ApiOperation("后台登录")
    @PostMapping("admin-login")
    public R<UserAdminDo> adminLogin(@RequestBody UserAdminLoginPo userAdminLoginPo){
        return new APITemplate<UserAdminDo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected UserAdminDo process() throws BizException {
                return userAdminService.adminLogin(userAdminLoginPo);
            }
        }.execute();
    }


    @ApiOperation("增加/修改管理员信息")
    @PostMapping("add-update-admin")
    public R<Boolean> addUpdateAdmin(@RequestBody UserAdminAddPo userAdminAddPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userAdminService.addUpdateAdmin(userAdminAddPo);
            }
        }.execute();
    }

    @ApiOperation("删除管理员")
    @GetMapping("delete-admin")
    public R<Boolean> deleteAdmin(Integer id){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userAdminService.deleteAdmin(id);
            }
        }.execute();
    }

    @ApiOperation("查询下级管理员")
    @GetMapping("/admins-under")
    public R<Page<UserAdminDo>> adminsUnder(UserAdminPagePo userAdminPagePo){
        return new APITemplate<Page<UserAdminDo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<UserAdminDo> process() throws BizException {
                return userAdminService.adminsUnder(userAdminPagePo);
            }
        }.execute();
    }


    @ApiOperation("查询管理员by id")
    @GetMapping("admin-by-id")
    public R<UserAdminDo> adminById(Integer id){
        return new APITemplate<UserAdminDo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected UserAdminDo process() throws BizException {
                return userAdminService.adminById(id);
            }
        }.execute();
    }



    @ApiOperation("添加/修改用户")
    @PostMapping("add-update-user")
    public R<Boolean> addOrUpdateUser(@RequestBody UserAddPo userAddPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userAdminService.addOrUpdateUser(userAddPo);
            }
        }.execute();
    }


    @ApiOperation("删除用户")
    @GetMapping("delete-user-by-id")
    public R<Boolean> deleteUserById(Integer id){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userAdminService.deleteUserById(id);
            }
        }.execute();
    }

    @ApiOperation("修改密码-管理员")
    @PostMapping("update-pwd")
    public R<Boolean> updatePwd(Integer id,String oldPwd,String newPwd){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return userAdminService.updatePwd(id,oldPwd,newPwd);
            }
        }.execute();
    }

}
