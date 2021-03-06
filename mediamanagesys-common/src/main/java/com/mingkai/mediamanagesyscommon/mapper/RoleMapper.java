package com.mingkai.mediamanagesyscommon.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mediamanagesyscommon.model.Do.uc.RoleDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-05 11:46
 */
@Repository
@Mapper
public interface RoleMapper extends BaseMapper<RoleDo> {
}
