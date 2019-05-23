package com.mingkai.mediamanagesysmapper.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mingkai.mediamanagesysmapper.model.Do.movie.MovieWriterDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-02 16:02
 */
@Repository
@Mapper
public interface MovieWriterMapper extends BaseMapper<MovieWriterDo> {
}
