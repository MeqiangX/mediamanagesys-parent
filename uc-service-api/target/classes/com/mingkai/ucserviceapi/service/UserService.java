package com.mingkai.ucserviceapi.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mingkai.mappermodule.common.MessageEnum;
import com.mingkai.mappermodule.manager.RoleManager;
import com.mingkai.mappermodule.manager.UserRoleRelManager;
import com.mingkai.mappermodule.mapper.UserInfoMapper;
import com.mingkai.mappermodule.mapper.UserMapper;
import com.mingkai.mappermodule.model.Do.uc.RoleDo;
import com.mingkai.mappermodule.model.Do.uc.UserDO;
import com.mingkai.mappermodule.model.Do.uc.UserInfoDo;
import com.mingkai.mappermodule.model.Do.uc.UserRoleRelDo;
import com.mingkai.mappermodule.model.MailModel;
import com.mingkai.mappermodule.model.Po.uc.*;
import com.mingkai.mappermodule.utils.CodeUtil;
import com.mingkai.mappermodule.utils.convert.ConvertUtil;
import com.mingkai.mappermodule.utils.redis.RedisUtil;
import com.mingkai.ucserviceapi.manager.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @description:
 * @author: Created by 云风 on 2019-03-30 21:02
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserManager userManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private UserRoleRelManager userRoleRelManager;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ThymeleafService thymeleafService;

    @Autowired
    private SessionService sessionService;

    /**
     * 根据id 查询用户
     * @param id
     * @return
     */
    public UserDO userById(Integer id){
        return userMapper.selectById(id);
    }

    /**
     * 查询用户 分页 phone or user_name
     * @param userPagePo
     * @return
     */
    public Page<UserDO> userPages(UserPagePo userPagePo){

        Page<UserDO> userPages
                = (Page<UserDO>)userMapper.usersPage(userPagePo);

        return userPages;
    }

    /**
     * 得到登录用户的信息 from session
     * @param userId
     * @return
     */
    public UserDO getLoginUser(Integer userId){

        sessionService.getSession().getId();
        Object users = sessionService.getAttribute("users");

        if (Objects.isNull(users)){
            sessionService.setAttribute("users",Lists.newArrayList());
        }

        List<UserDO> userlist = (List)sessionService.getAttribute("users");

        for (UserDO userDO : userlist) {

            if (userDO.getId().equals(userId)){
                return userDO;
            }

        }
        return null;
    }


    /**
     * 得到验证码
     * @param messagePo
     * @return
     */
    public String getCode(MessagePo messagePo){

        Object code = redisUtil.get(messagePo.getSendType() + "+" + messagePo.getReceverPhone());

        if (Objects.isNull(code)){
            throw new BizException("验证码已失效");
        }

        return code.toString();

    }


    /**
     * 修改密码
     * @param pwdUpdatePo
     * @return
     */
    public Boolean updatePwd(PwdUpdatePo pwdUpdatePo){
        UserDO userDO = new UserDO();
        userDO.setPhone(pwdUpdatePo.getPhone());
        userDO.setUserPassword(pwdUpdatePo.getNewPwd());
        int updateCount = userMapper.update(userDO, new UpdateWrapper<UserDO>()
                .eq("phone", userDO.getPhone())
                .eq("status",0));

        return updateCount == 1;
    }

    /**
     * 登录service
     * @param loginPo
     * @return
     */
    public UserDO login(LoginPo loginPo){

        /**
         * 正常登录 登录成功之后的信息放入session 中
         */
        if (loginPo.getLoginOption().equals(MessageEnum.LOGIN.getVal())){
            Optional<UserDO> userDOOptional = Optional.fromNullable(userMapper.login(loginPo.getAccount(), loginPo.getPassword()));

            if (userDOOptional.isPresent()){
                //如果登录成功 则非空
                UserDO user = userDOOptional.get();
                user.setUserPassword(null);
                // 存储到session 中

                sessionService.getSession().getId();
                Object users = sessionService.getAttribute("users");

                if (Objects.isNull(users)){ // 前端在登录完成后 将用户id 保存在cookies 中  然后每次通过拿到cookies的用户id 来查询用户状态
                    sessionService.setAttribute("users",Lists.newArrayList());
                }

                List<UserDO> userList = (List) sessionService.getAttribute("users");

                for (UserDO userDO : userList) {
                    if (user.getId().equals(userDO.getId())){
                        log.info("当前用户已经登录  -- > " + userDO.getUserName());
                        return user;
                    }
                }

                // 当前session 中没有当前的登录对象 放入session中 清空密码
                userList.add(user);

                //放回session中
                sessionService.setAttribute("users",userList);

                return user;
            }

            throw new BizException("用户名或密码错误");

        }
        if (loginPo.getLoginOption().equals(MessageEnum.LOGIN_PHONE.getVal())){
            // 手机验证码登录

            UserDO userDO = registedPhone(loginPo.getAccount());
            userDO.setUserPassword(null);
            //对比当前手机号 和 验证码

            Object code = redisUtil.get(MessageEnum.LOGIN_PHONE.getVal() + "+" + loginPo.getAccount());

            if (Objects.nonNull(code)){
                // 非空 和当前输入的验证码 比较
                if (code.toString().equals(loginPo.getCode())){
                    // 清除验证码
                    redisUtil.del(MessageEnum.LOGIN_PHONE.getVal() + "+" + loginPo.getAccount());
                    return userDO;
                }
            }

            throw new BizException("验证码错误");
        }
        if (loginPo.getLoginOption().equals(MessageEnum.LOGIN_EMAIL.getVal())){
            // 邮箱登录

            UserDO userDO = registedEmail(loginPo.getAccount());
            userDO.setUserPassword(null);

            // 对比当前邮箱 和 验证码
            Object rightCode = redisUtil.get(loginPo.getLoginOption() + "+" + loginPo.getAccount());

            if (Objects.nonNull(rightCode)){
                if (rightCode.equals(loginPo.getCode())){
                    //清除redis
                    redisUtil.del(loginPo.getLoginOption() + "+" + loginPo.getAccount());
                    return userDO;
                }
            }

            throw new BizException("验证码错误");
        }

        throw new BizException("错误代码");
    }


    /**
     * 注冊
     * @param registerPo
     * @return
     */
    @Transactional
    public Boolean register(RegisterPo registerPo){

        // 手机注册
        String key = registerPo.getRegisterOption() + "+" +
                (registerPo.getRegisterOption().equals(MessageEnum.REGISTER_PHONE.getVal()) ? registerPo.getPhone()
                : registerPo.getEmail());

        if (registerPo.getRegisterOption().equals(MessageEnum.REGISTER_PHONE.getVal())){

            if (Objects.nonNull(registedPhone(registerPo.getPhone()))){
                throw new BizException("手机号已经被注册过!");
            }

            Object rightCode = redisUtil.get(registerPo.getRegisterOption() + "+" + registerPo.getPhone());

            if (Objects.isNull(rightCode) || !rightCode.toString().equals(registerPo.getCode())){
                throw new BizException("验证码不正确");
            }
        }

        // 邮箱注册
        if (registerPo.getRegisterOption().equals(MessageEnum.REGISTER_EMAIL.getVal())){

            if (Objects.nonNull(registedEmail(registerPo.getEmail()))){
                throw new BizException("邮箱已经被注册过!");
            }

            Object rightCode = redisUtil.get(registerPo.getRegisterOption() + "+" + registerPo.getEmail());

            if (Objects.isNull(rightCode) || !rightCode.toString().equals(registerPo.getCode())){
                throw new BizException("验证码不正确");
            }
        }


        //验证过验证码  再验证名称是否重复 --> 完成注册
        if (repeatName(registerPo.getUserName())){
            throw new BizException("名称重复!");
        }

        // 完成注册
        UserDO userDO = new UserDO();
        userDO.setUserName(registerPo.getUserName());
        userDO.setUserPassword(registerPo.getPassword());
        userDO.setEmail(registerPo.getEmail());
        userDO.setPhone(registerPo.getPhone());
        userDO.setStatus(0);

        Boolean success = 1 == userMapper.insert(userDO);

        // 插入的同时 添加一条用户信息
        UserInfoDo userInfoDo = new UserInfoDo();

        userInfoDo.setUserId(userDO.getId());

        userInfoMapper.insert(userInfoDo);

        if (success){
            // 清除redis 的 验证信息
            redisUtil.del(key);
        }

        return success;
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


    /**
     * 注册手机验证
     * @param phone
     * @return
     */
    public UserDO registedPhone(String phone){
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>()
                .eq("status", 0)
                .eq("phone", phone));

        if (Objects.nonNull(userDO)){
            return userDO;
        }

        return null;
    }

    /**
     * 注册邮箱验证
     * @param email
     * @return
     */
    public UserDO registedEmail(String email){
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>()
                .eq("status", 0)
                .eq("email", email));


            return userDO;

    }

    /**
     * 重名校验
     * @return
     */
    public Boolean repeatName(String userName){

        List<UserDO> userDOS = userMapper.selectList(new QueryWrapper<UserDO>()
                .eq("status", 0)
                .eq("user_name", userName));

        if (Objects.nonNull(userDOS) && userDOS.size() != 0){
            return true;
        }
        return false;
    }


    /**
     * 给用户赋予角色
     * @param userRoleAddPo
     * @return
     */
    @Transactional
    public Boolean addRoleToUser(UserRoleAddPo userRoleAddPo){

        // 检查角色
        UserDO userDO = userMapper.selectById(userRoleAddPo.getUserId());
        if (Objects.isNull(userDO)){
            throw new BizException("用户不存在");
        }
        // 不存在角色
        List<RoleDo> roleDos = (List<RoleDo>)roleManager.listByIds(userRoleAddPo.getRoleIds());
        List<Integer> notContainId = Lists.newArrayList();
        if (roleDos.size() < userRoleAddPo.getRoleIds().size()){
            for (Integer roleId : userRoleAddPo.getRoleIds()) {
                if (!roleDos.contains(roleId)){
                    notContainId.add(roleId);
                }
            }

            throw new BizException("存在无效的角色信息：" + Strings.join(notContainId,','));
        }

        // 重复角色

        // 查找用户已经有的角色
        List<UserRoleRelDo> userRoleRelDoList = userRoleRelManager.list(new QueryWrapper<UserRoleRelDo>()
                .eq("user_id", userRoleAddPo.getUserId()));

        List<Integer> repeatRoles = Lists.newArrayList();

        for (UserRoleRelDo userRoleRelDo : userRoleRelDoList) {
            if (userRoleAddPo.getRoleIds().contains(userRoleRelDo.getRoleId())){
                repeatRoles.add(userRoleRelDo.getRoleId());
            }
        }

        if (Objects.nonNull(repeatRoles) && repeatRoles.size() > 0){
            throw new BizException("含有重复角色：" + Strings.join(repeatRoles,','));
        }

        // 插入记录

        List<UserRoleRelDo> insertList = Lists.newArrayList();

        for (Integer roleId : userRoleAddPo.getRoleIds()) {

            UserRoleRelDo userRoleRelDo = new UserRoleRelDo();
            userRoleRelDo.setUserId(userRoleAddPo.getUserId());
            userRoleRelDo.setRoleId(roleId);
            insertList.add(userRoleRelDo);
        }


        // 执行插入
        return userRoleRelManager.saveBatch(insertList);


    }


    /**
     * 登出
     * @param userId
     * @return
     */
    public Boolean logout(Integer userId){

        //将session users--> userId = userId clear

        List<UserDO> users = (List)sessionService.getAttribute("users");

        if (Objects.isNull(users) || users.size() == 0){
            return true;
        }


        Integer removeId = null;
        for (int i = 0; i < users.size();++i){
            if (users.get(i).getId().equals(userId)){
                removeId = i;
                break;
            }
        }

        if (removeId != null){
            int i = removeId;
            users.remove(i);
        }

        sessionService.setAttribute("users",users);
        return Boolean.TRUE;

    }

    /**
     * 取得用户信息 by userid
     * @param userId
     * @return
     */
    public UserInfoDo userInfoById(Integer userId){

        UserInfoDo userInfoDo = userInfoMapper.selectOne(new QueryWrapper<UserInfoDo>()
                .eq("user_id", userId));

        if (Objects.isNull(userInfoDo)){
            throw new BizException("用户不存在");
        }


        return userInfoDo;

    }


    /**
     * 修改用户基本信息
     * @param userInfoPo
     * @return
     */
    public Boolean updateUserInfo(UserInfoPo userInfoPo){

        UserInfoDo convert = ConvertUtil.convert(userInfoPo, UserInfoDo.class);

        int update = userInfoMapper.update(convert, new UpdateWrapper<UserInfoDo>()
                .eq("user_id", userInfoPo.getUserId()));

        return update == 1;

    }
}
