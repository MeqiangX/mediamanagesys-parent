package com.mingkai.mediamanagesyscommon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mediamanagesyscommon.model.Do.cinema.CinemaDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 9:15
 */
@Repository
@Mapper
public interface CinemaMapper extends BaseMapper<CinemaDo> {
}