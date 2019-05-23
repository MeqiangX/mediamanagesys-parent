package com.mingkai.mappermodule.manager;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingkai.mappermodule.mapper.RoleMapper;
import com.mingkai.mappermodule.model.Do.uc.RoleDo;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-08 14:22
 */
@Repository
public class RoleManager extends ServiceImpl<RoleMapper, RoleDo> {
}
