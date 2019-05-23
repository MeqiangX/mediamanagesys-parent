package com.mingkai.mediamanagesysuc.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.mingkai.mediamanagesysmapper.manager.UserAdminManager;
import com.mingkai.mediamanagesysmapper.mapper.UserInfoMapper;
import com.mingkai.mediamanagesysmapper.mapper.UserMapper;
import com.mingkai.mediamanagesysmapper.model.Do.UserAdminDo;
import com.mingkai.mediamanagesysmapper.model.Do.uc.UserDO;
import com.mingkai.mediamanagesysmapper.model.Do.uc.UserInfoDo;
import com.mingkai.mediamanagesysmapper.model.Po.uc.UserAddPo;
import com.mingkai.mediamanagesysmapper.model.Po.uc.UserAdminAddPo;
import com.mingkai.mediamanagesysmapper.model.Po.uc.UserAdminLoginPo;
import com.mingkai.mediamanagesysmapper.model.Po.uc.UserAdminPagePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-07 9:44
 */
@Service
@Slf4j
public class UserAdminService {

    @Autowired
    private UserAdminManager userAdminManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;


    /**
     * 后台登录
     * @param userAdminLoginPo
     * @return
     */
    public UserAdminDo adminLogin(UserAdminLoginPo userAdminLoginPo){

        UserAdminDo userAdminDo = userAdminManager.getOne(new QueryWrapper<UserAdminDo>()
                .eq("user_name", userAdminLoginPo.getUserName())
                .eq("user_password", userAdminLoginPo.getUserPassword()));

        if (Objects.nonNull(userAdminDo)){
            if (userAdminDo.getStatus() != 0){
                throw new BizException("账号已冻结，请与管理员联系");
            }
            return userAdminDo;
        }else{

            throw new BizException("账号或密码错误");

        }



    }


    /**
     * 管理员修改密码
     * @param id
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public Boolean updatePwd(Integer id,String oldPwd,String newPwd){

        // 根据id 查找用户密码 是否正确

        UserAdminDo userAdmin = userAdminManager.getById(id);

        if (Objects.isNull(userAdmin)){
            throw new BizException("用户不存在");
        }

        // 看密码是否相同

        if (!oldPwd.equals(userAdmin.getUserPassword())){
            throw new BizException("密码错误");
        }


        // 执行修改
        return userAdminManager.getBaseMapper().updatePwd(id,newPwd);
    }

    /**
     * 添加/修改 管理员
     * @param userAdminAddPo
     */
    public Boolean addUpdateAdmin(UserAdminAddPo userAdminAddPo){

        UserAdminDo userAdminDo = new UserAdminDo();

        userAdminDo.setId(userAdminAddPo.getId());
        userAdminDo.setUserName(userAdminAddPo.getUserName());
        userAdminDo.setUserPassword(userAdminAddPo.getUserPassword());
        userAdminDo.setStatus(userAdminAddPo.getStatus());
        userAdminDo.setParent(userAdminAddPo.getParent());


        if (Objects.isNull(userAdminAddPo.getId())){

            // 新增  重名检验
            UserAdminDo userNameVerifity = userAdminManager.getOne(new QueryWrapper<UserAdminDo>()
                    .eq("user_name", userAdminAddPo.getUserName()));

            if (Objects.nonNull(userNameVerifity)){
                throw new BizException("账号重复!");
            }

            return userAdminManager.save(userAdminDo);

        }else{

            // 修改   重名检验
            // 新增  重名检验
            UserAdminDo userNameVerifity = userAdminManager.getOne(new QueryWrapper<UserAdminDo>()
                    .eq("user_name", userAdminAddPo.getUserName())
                    .ne("id",userAdminAddPo.getId()));

            if (Objects.nonNull(userNameVerifity)){
                throw new BizException("账号重复!");
            }

            return userAdminManager.updateById(userAdminDo);

        }
    }


    /**
     * 删除管理员 关联下级的管理员一起删除
     * @param id
     * @return
     */
    @Transactional
    public Boolean deleteAdmin(Integer id){


        UserAdminDo userAdminDo = adminById(id);

        //得到 下级

        List<UserAdminDo> userAdminDos = userAdminManager.list(new QueryWrapper<UserAdminDo>()
                .eq("parent", userAdminDo.getId()));

        // 批量删除
        if (Objects.nonNull(userAdminDos) && userAdminDos.size() != 0){
            userAdminManager.removeByIds(userAdminDos.stream().map(UserAdminDo::getId).collect(Collectors.toList()));
        }


        // 删除自己
        return userAdminManager.removeById(id);
    }


    /**
     * 查询管理员 by id
     * @param id
     * @return
     */
    public UserAdminDo adminById(Integer id){
        return userAdminManager.getById(id);
    }

    /**
     * 查找下级管理员
     * @param userAdminPagePo
     * @return
     */
    public Page<UserAdminDo> adminsUnder(UserAdminPagePo userAdminPagePo){

        Page<UserAdminDo> result = userAdminManager.getBaseMapper().userAdminPages(userAdminPagePo);

        return result;
    }


    /**
     * 添加/修改用户
     * @param userAddPo
     * @return
     */
    @Transactional
    public Boolean addOrUpdateUser(UserAddPo userAddPo){

        UserDO userDO = new UserDO();
        userDO.setId(userAddPo.getId());
        userDO.setUserName(userAddPo.getUserName());
        userDO.setUserPassword(userAddPo.getUserPassword());
        userDO.setPhone(userAddPo.getPhone());
        userDO.setEmail(userAddPo.getEmail());
        userDO.setStatus(userAddPo.getStatus());

        if (Objects.isNull(userDO.getId())){

            // 添加  手机 邮箱是否被注册 是否重名
            UserDO userPhoneVerfify = userMapper.selectOne(new QueryWrapper<UserDO>()
                    .eq("phone", userAddPo.getPhone()));


            UserDO emailVerfify = userMapper.selectOne(new QueryWrapper<UserDO>()
                    .eq("email", userDO.getEmail()));

            UserDO userNameVerifify = userMapper.selectOne(new QueryWrapper<UserDO>()
                    .eq("user_name", userDO.getUserName()));

            if (Objects.nonNull(userPhoneVerfify)){
                throw new BizException("手机已经被注册");
            }else if (Objects.nonNull(emailVerfify) && userDO.getEmail() != null && userDO.getEmail() != ""){
                throw new BizException("邮箱已经被注册");
            }else if (Objects.nonNull(userNameVerifify)){
                throw new BizException("名称重复");
            }else{

                // 添加的同时 添加个人信息表中
                int insertResult = userMapper.insert(userDO);

                UserInfoDo userInfoDo = new UserInfoDo();
                userInfoDo.setUserId(userDO.getId());
                int insert = userInfoMapper.insert(userInfoDo);

                return insertResult == 1 && insert == 1;
            }

        }else{

            // 修改

            // 除了自己  是否 手机 邮箱 是否被注册 是否重名

            UserDO userPhoneVerfify = userMapper.selectOne(new QueryWrapper<UserDO>()
                    .eq("phone", userAddPo.getPhone())
                    .ne("id",userAddPo.getId()));

            UserDO emailVerfify = userMapper.selectOne(new QueryWrapper<UserDO>()
                    .eq("email", userDO.getEmail())
                    .ne("id",userAddPo.getId()));

            UserDO userNameVerifify = userMapper.selectOne(new QueryWrapper<UserDO>()
                    .eq("user_name", userDO.getUserName())
                    .ne("id",userAddPo.getId()));

            if (Objects.nonNull(userPhoneVerfify)){
                throw new BizException("手机已经被注册");
            }else if (Objects.nonNull(emailVerfify) && userDO.getEmail() != null && userDO.getEmail() != ""){
                throw new BizException("邮箱已经被注册");
            }else if (Objects.nonNull(userNameVerifify)){
                throw new BizException("名称重复");
            }else{
                return 1 == userMapper.updateById(userDO);
            }

        }

    }

    /**
     *  删除用户
     * @param id
     * @return
     */
    @Transactional
    public Boolean deleteUserById(Integer id){

        // 会将用户的订单也一块删除  在删除之前 请求订单接口 删除

        // 删除用户信息
        int deleteUserInfo = userInfoMapper.delete(new QueryWrapper<UserInfoDo>()
                .eq("user_id", id));

        // 删除用户
        int userDelete = userMapper.deleteById(id);

        return Boolean.TRUE;

    }

}
