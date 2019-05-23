package com.mingkai.mappermodule.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mappermodule.model.Do.movie.MovieBlooperDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-02 16:00
 */
@Repository
@Mapper
public interface MovieBlooperMapper extends BaseMapper<MovieBlooperDo> {
}
