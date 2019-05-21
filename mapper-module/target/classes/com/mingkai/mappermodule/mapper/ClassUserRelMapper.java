package com.mingkai.mappermodule.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mappermodule.model.Do.order.ClassUserRelDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 16:10
 */
@Repository
@Mapper
public interface ClassUserRelMapper extends BaseMapper<ClassUserRelDo> {
}
