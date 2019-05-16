package com.mingkai.ucserviceapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mappermodule.common.API;
import com.mingkai.mappermodule.model.Po.uc.PermAddPo;
import com.mingkai.mappermodule.model.Vo.uc.PermisionVo;
import com.mingkai.ucserviceapi.service.PermisionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 权限Controller
 * @author: Created by 云风 on 2019-04-08 17:02
 */
@RestController
@RequestMapping(API.API_PERMISION)
@Api(tags = API.PERMISION)
public class PermisionController {

    @Autowired
    private PermisionService permisionService;

    @ApiOperation("测试权限")
    @GetMapping("permision-test")
    public R<Boolean> permisionTest(){
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


    @ApiOperation("分页权限列表")
    @GetMapping("permision-list")
    public R<Page<PermisionVo>> permisionList(Page page){
        return new APITemplate<Page<PermisionVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<PermisionVo> process() throws BizException {
                return permisionService.permisionList(page);
            }
        }.execute();
    }


    @ApiOperation("添加权限")
    @PostMapping("permision-add")
    public R<Boolean> addPermision(PermAddPo permAddPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return permisionService.addPermision(permAddPo);
            }
        }.execute();
    }

    @ApiOperation("通过id查询权限")
    @GetMapping("permision-search-id")
    public R<PermisionVo> permisionSearchById(Integer id){
        return new APITemplate<PermisionVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected PermisionVo process() throws BizException {
                return permisionService.searchById(id);
            }
        }.execute();
    }
}
