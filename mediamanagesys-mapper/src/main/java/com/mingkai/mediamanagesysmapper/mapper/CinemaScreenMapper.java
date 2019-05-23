package com.mingkai.mediamanagesysmapper.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mediamanagesysmapper.model.Do.cinema.CinemaScreenDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 9:50
 */
@Repository
@Mapper
public interface CinemaScreenMapper extends BaseMapper<CinemaScreenDo> {
}
