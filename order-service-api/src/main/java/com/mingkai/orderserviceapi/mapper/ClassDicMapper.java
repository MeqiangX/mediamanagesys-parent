package com.mingkai.orderserviceapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.systemcommon.model.Do.order.ClassDicDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 16:08
 */
@Repository
@Mapper
public interface ClassDicMapper extends BaseMapper<ClassDicDo> {
}
