package com.mingkai.mappermodule.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mappermodule.model.Do.UserAdminDo;
import com.mingkai.mappermodule.model.Po.uc.UserAdminPagePo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-04 20:20
 */
@Repository
@Mapper
public interface UserAdminMapper extends BaseMapper<UserAdminDo> {

    Page<UserAdminDo> userAdminPages(@Param("userAdminPagePo") UserAdminPagePo userAdminPagePo);

    Boolean updatePwd(@Param("id") Integer id, @Param("newPwd") String newPwd);

}
