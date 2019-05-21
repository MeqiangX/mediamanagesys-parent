package com.mingkai.ucserviceapi.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.google.common.collect.Lists;
import com.mingkai.mappermodule.manager.PermisionManager;
import com.mingkai.mappermodule.manager.RoleManager;
import com.mingkai.mappermodule.manager.RolePermRelManager;
import com.mingkai.mappermodule.model.Do.uc.PermisionDo;
import com.mingkai.mappermodule.model.Do.uc.RoleDo;
import com.mingkai.mappermodule.model.Do.uc.RolePermRelDo;
import com.mingkai.mappermodule.model.Po.uc.PermRoleAddPo;
import com.mingkai.mappermodule.model.Po.uc.PermisionPagePo;
import com.mingkai.mappermodule.model.Po.uc.RoleAddPo;
import com.mingkai.mappermodule.model.Vo.uc.PermisionVo;
import com.mingkai.mappermodule.model.Vo.uc.RoleVo;
import com.mingkai.mappermodule.utils.convert.ConvertUtil;
import com.mingkai.mappermodule.utils.page.PageUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 角色Service
 * @author: Created by 云风 on 2019-04-08 14:16
 */
@Service
public class RoleService {

    @Autowired
    private RoleManager roleManager;

    @Autowired
    private RolePermRelManager rolePermRelManager;

    @Autowired
    private PermisionManager permisionManager;

    public Page<RoleVo> searchAllRolesByPage(Page page){
        Page<RoleVo> roles = (Page) roleManager.page(page);
        return roles;
    }

    public RoleVo searchRoleById(Integer id){
        RoleDo role = roleManager.getById(id);
        if (null == role){
            throw new BizException("没有查找到该id的角色");
        }
        return ConvertUtil.convert(role,RoleVo.class);
    }


    /**
     * 分页查询 角色具有的权限列表
     * @param permisionPagePo
     * @return
     */
    public Page<PermisionVo> rolePermisions(PermisionPagePo permisionPagePo){

        // 分页查询角色的 权限
        Page<RolePermRelDo> rolePermRelDoPage = (Page)rolePermRelManager.page(permisionPagePo, new QueryWrapper<RolePermRelDo>()
                .eq("role_id", permisionPagePo.getRoleId()));

        List<RolePermRelDo> records =
                rolePermRelDoPage.getRecords();

        // records 是当前角色的 当前 current  pageSize 的 权限idS

        //根据ids 去查询 权限详情

        List<Integer> permisionIds = records.stream().map(RolePermRelDo::getPermisionId).collect(Collectors.toList());

        // 根据ids 查询权限

        if (Objects.isNull(permisionIds) || permisionIds.size() == 0){
            return new Page<>();
        }

        List<PermisionDo> permisions = (List<PermisionDo>) permisionManager.listByIds(permisionIds);

        Page<PermisionDo> page = PageUtils.page(permisions, rolePermRelDoPage);

        Page<PermisionVo> permisionVoPage = ConvertUtil.pageConvert(page, PermisionVo.class);

        return permisionVoPage;

    }

    /**
     * 分配权限to role
     * @param permRoleAddPo
     * @return
     */
    @Transactional
    public Boolean addPermToRole(PermRoleAddPo permRoleAddPo){

        //检查  是否存在角色
        RoleDo role = roleManager.getById(permRoleAddPo.getRoleId());

        if (Objects.isNull(role)){
            throw new BizException("未找到该角色的信息");
        }

        // 查找权限列表
        List<RolePermRelDo> rolePermRelDoList = rolePermRelManager.list(new QueryWrapper<RolePermRelDo>()
                .eq("role_id", permRoleAddPo.getRoleId()));

        // 查询该角色具有的权限
        List<Integer> rolePermisions = rolePermRelDoList.stream().map(RolePermRelDo::getPermisionId).collect(Collectors.toList());

        // 是否已经被分配过
        List<Integer> repeatPerms = Lists.newArrayList();
        for (Integer rolePermision : rolePermisions) {
            if (permRoleAddPo.getPermisionList().contains(rolePermision)){
                repeatPerms.add(rolePermision);
            }
        }
        // 已经被分配过 抛出
        if (repeatPerms.size() != 0){
            throw new BizException("存在重复分配的权限："+Strings.join(repeatPerms,','));
        }

        // 未被分配 执行分配

        List<RolePermRelDo> rolePermRelAddDos = Lists.newArrayList();

        for (Integer permisionId : permRoleAddPo.getPermisionList()) {
            RolePermRelDo rolePermRelDo = new RolePermRelDo();
            rolePermRelDo.setRoleId(permRoleAddPo.getRoleId());
            rolePermRelDo.setPermisionId(permisionId);
            rolePermRelAddDos.add(rolePermRelDo);
        }

        return rolePermRelManager.saveBatch(rolePermRelAddDos);
    }

    /**
     * 移除权限 From Role
     * @param permRoleAddPo
     * @return
     */
    @Transactional
    public Boolean removePermFromRole(PermRoleAddPo permRoleAddPo){

        //检查  是否存在角色
        RoleDo role = roleManager.getById(permRoleAddPo.getRoleId());

        if (Objects.isNull(role)){
            throw new BizException("未找到该角色的信息");
        }

        if (permRoleAddPo.getPermisionList().size() == 0){
            throw new BizException("为选中要操作的权限");
        }

        // 查找权限列表
        List<RolePermRelDo> rolePermRelDoList = rolePermRelManager.list(new QueryWrapper<RolePermRelDo>()
                .eq("role_id", permRoleAddPo.getRoleId()));

        // 查询该角色具有的权限
        List<Integer> rolePermisions = rolePermRelDoList.stream().map(RolePermRelDo::getPermisionId).collect(Collectors.toList());


        // 角色并没有任何权限
        if (Objects.isNull(rolePermisions) || rolePermisions.size() == 0){
            throw new BizException("该角色下没有任何权限");
        }

        // 是否在具有的权限中
        List<Integer> notHavePerms = Lists.newArrayList();
        for (Integer rolePermision : rolePermisions) {
            if (!permRoleAddPo.getPermisionList().contains(rolePermision)){
                notHavePerms.add(rolePermision);
            }
        }

        // 有一些不在 抛出
        if (notHavePerms.size() != 0){
            throw new BizException("一些权限并不在此角色下："+Strings.join(notHavePerms,','));
        }

        // 都是已经分配的权限 执行

        return rolePermRelManager.remove(new QueryWrapper<RolePermRelDo>()
        .eq("role_id",permRoleAddPo.getRoleId())
        .in("permision_id",permRoleAddPo.getPermisionList()));
    }


    /**
     * 添加角色
     * @param roleAddPo
     * @return
     */
    @Transactional
    public Boolean addRole(RoleAddPo roleAddPo){

        //重名校验
        List<RoleDo> repeatNames = roleManager.list(new QueryWrapper<RoleDo>()
                .eq("role_name", roleAddPo.getRoleName()));

        if (Objects.nonNull(repeatNames) && repeatNames.size() != 0){
            throw new BizException("存在同名的角色,请修改");
        }

        //添加
        RoleDo roleDo = new RoleDo();
        roleDo.setRoleName(roleAddPo.getRoleName());
        roleDo.setDescription(roleAddPo.getDescription());
        return roleManager.save(roleDo);
    }
}
