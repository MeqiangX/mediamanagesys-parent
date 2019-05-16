package com.mingkai.ucserviceapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.base.Checks;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mappermodule.common.API;
import com.mingkai.mappermodule.model.Po.uc.PermRoleAddPo;
import com.mingkai.mappermodule.model.Po.uc.PermisionPagePo;
import com.mingkai.mappermodule.model.Po.uc.RoleAddPo;
import com.mingkai.mappermodule.model.Vo.uc.PermisionVo;
import com.mingkai.mappermodule.model.Vo.uc.RoleVo;
import com.mingkai.ucserviceapi.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 角色管理Controller
 * @author: Created by 云风 on 2019-04-05 11:49
 */
@RestController
@RequestMapping(API.API_ROLE)
@Api(tags = API.ROLE)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("角色模块测试")
    @GetMapping("role-test")
    public R<Boolean> roleTest(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return Boolean.TRUE;
            }
        }.execute();
    }

    @ApiOperation("查询所有的角色")
    @GetMapping("role-search-all")
    public R<Page<RoleVo>> roleSearchAll(Page page){
        return new APITemplate<Page<RoleVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<RoleVo> process() throws BizException {
                return roleService.searchAllRolesByPage(page);
            }
        }.execute();
    }

    @ApiOperation("根据id查询角色")
    @GetMapping("role-search-id")
    public R<RoleVo> roleSearchById(@RequestParam("id") Integer id){
        return new APITemplate<RoleVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected RoleVo process() throws BizException {
                return roleService.searchRoleById(id);
            }
        }.execute();
    }


    @ApiOperation("查询角色拥有的权限")
    @GetMapping("role-permisions")
    public R<Page<PermisionVo>> rolePermisions(PermisionPagePo permisionPagePo){
        return new APITemplate<Page<PermisionVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<PermisionVo> process() throws BizException {
                return roleService.rolePermisions(permisionPagePo);
            }
        }.execute();
    }

    @ApiOperation("给角色分配权限")
    @PostMapping("role-permisions-add")
    public R<Boolean> addPermToRole(PermRoleAddPo permRoleAddPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {
                Checks.nonNull(permRoleAddPo,"参数不能为空");
                Checks.nonNull(permRoleAddPo.getRoleId(),"角色id不能为空");
                Checks.nonNull(permRoleAddPo.getPermisionList(),"权限ids不能为空");
                Checks.isFalse(permRoleAddPo.getPermisionList().size() == 0,"权限列表不能为空");
            }

            @Override
            protected Boolean process() throws BizException {
                return roleService.addPermToRole(permRoleAddPo);
            }
        }.execute();
    }

    @ApiOperation("给角色移除权限")
    @PostMapping("role-permisions-remove")
    public R<Boolean> removePermFromRole(PermRoleAddPo permRoleAddPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {
                Checks.nonNull(permRoleAddPo,"参数不能为空");
                Checks.nonNull(permRoleAddPo.getRoleId(),"角色id不能为空");
                Checks.nonNull(permRoleAddPo.getPermisionList(),"权限ids不能为空");
                Checks.isFalse(permRoleAddPo.getPermisionList().size() == 0,"权限列表不能为空");
            }

            @Override
            protected Boolean process() throws BizException {
                return roleService.removePermFromRole(permRoleAddPo);
            }
        }.execute();
    }

    @ApiOperation("创建角色")
    @PostMapping("role-add")
    public R<Boolean> addRole(RoleAddPo roleAddPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {
                Checks.nonNull(roleAddPo);
                Checks.nonNull(roleAddPo.getRoleName());
                Checks.hasText(roleAddPo.getRoleName());
            }

            @Override
            protected Boolean process() throws BizException {
                return roleService.addRole(roleAddPo);
            }
        }.execute();
    }
}
