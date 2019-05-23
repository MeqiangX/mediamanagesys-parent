package com.mingkai.mediamanagesysmapper.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mediamanagesysmapper.model.Do.uc.RolePermRelDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-08 15:27
 */
@Repository
@Mapper
public interface RolePermRelMapper extends BaseMapper<RolePermRelDo> {
}
