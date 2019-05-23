package com.mingkai.mappermodule.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mappermodule.model.Do.uc.UserRoleRelDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-09 16:15
 */
@Mapper
@Repository
public interface UserRoleRelMapper extends BaseMapper<UserRoleRelDo> {
}
