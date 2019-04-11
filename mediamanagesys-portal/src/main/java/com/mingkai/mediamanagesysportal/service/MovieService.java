package com.mingkai.mediamanagesysportal.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dtstack.plat.lang.exception.BizException;
import com.mingkai.mediamanagesyscommon.manager.MovieDetailManager;
import com.mingkai.mediamanagesyscommon.model.Do.movie.MovieDetailDo;
import com.mingkai.mediamanagesyscommon.model.Vo.movie.MovieVo;
import com.mingkai.mediamanagesyscommon.utils.convert.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-11 9:32
 */
@Service
public class MovieService {

    @Autowired
    private MovieDetailManager movieDetailManager;

    /**
     * 通过movieId 查找电影详情
     * @param id
     * @return
     */
    public MovieVo movieDetailById(String id){

        MovieDetailDo movieDo = movieDetailManager.getOne(new QueryWrapper<MovieDetailDo>()
                .eq("movie_id", id));

        if (Objects.isNull(movieDo)){
            throw new BizException("电影不存在!");
        }

        return ConvertUtil.convert(movieDo,MovieVo.class);
    }

}
