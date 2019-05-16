package com.mingkai.mediamanagesysuc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mappermodule.model.Do.UserAdminDo;
import com.mingkai.mappermodule.model.Po.uc.UserAddPo;
import com.mingkai.mappermodule.model.Po.uc.UserAdminAddPo;
import com.mingkai.mappermodule.model.Po.uc.UserAdminLoginPo;
import com.mingkai.mappermodule.model.Po.uc.UserAdminPagePo;
import com.mingkai.mappermodule.utils.check.CheckOfR;
import com.mingkai.mediamanagesysuc.feigns.UserRpcFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-15 15:41
 */
@Service
public class UserAdminRpcService {

    @Autowired
    private UserRpcFeign userRpcFeign;


    public UserAdminDo adminLogin(UserAdminLoginPo userAdminLoginPo){
        return CheckOfR.check(userRpcFeign.adminLogin(userAdminLoginPo));
    }


    public Boolean addUpdateAdmin(UserAdminAddPo userAdminAddPo){
        return CheckOfR.check(userRpcFeign.addUpdateAdmin(userAdminAddPo));
    }


    public Boolean deleteAdmin(Integer id){
        return CheckOfR.check(userRpcFeign.deleteAdmin(id));
    }


    public Page<UserAdminDo> adminsUnder(UserAdminPagePo userAdminPagePo){
        return CheckOfR.check(userRpcFeign.adminsUnder(userAdminPagePo));
    }



    public UserAdminDo adminById(Integer id){
        return CheckOfR.check(userRpcFeign.adminById(id));
    }


    public Boolean addOrUpdateUser(UserAddPo userAddPo){
        return CheckOfR.check(userRpcFeign.addOrUpdateUser(userAddPo));
    }


    public Boolean deleteUserById(Integer id){
        return CheckOfR.check(userRpcFeign.deleteUserById(id));
    }

    public Boolean updatePwd(Integer id,String oldPwd,String newPwd){
        return CheckOfR.check(userRpcFeign.updatePwd(id,oldPwd,newPwd));
    }


}
