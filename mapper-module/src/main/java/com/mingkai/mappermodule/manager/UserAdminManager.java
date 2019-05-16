package com.mingkai.mappermodule.manager;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mingkai.mappermodule.mapper.UserAdminMapper;
import com.mingkai.mappermodule.model.Do.UserAdminDo;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-04 20:20
 */
@Repository
public class UserAdminManager extends ServiceImpl<UserAdminMapper, UserAdminDo> {
}
