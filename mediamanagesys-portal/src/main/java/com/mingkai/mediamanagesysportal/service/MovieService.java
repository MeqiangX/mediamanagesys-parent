package com.mingkai.mediamanagesysportal.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.mingkai.mediamanagesyscommon.common.MovieRankEnum;
import com.mingkai.mediamanagesyscommon.manager.MovieDetailManager;
import com.mingkai.mediamanagesyscommon.mapper.MovieRankMapper;
import com.mingkai.mediamanagesyscommon.model.Do.movie.MovieDetailDo;
import com.mingkai.mediamanagesyscommon.model.Do.movie.MovieRankDo;
import com.mingkai.mediamanagesyscommon.model.Po.movie.MovieRankPagePo;
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

    @Autowired
    private MovieRankMapper movieRankMapper;

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

    /**
     *  查询榜单  分页 条件 排序
     * @param movieRankPagePo
     * @return
     */
    public Page<MovieVo> movieRank(MovieRankPagePo movieRankPagePo){

        //表名
        movieRankPagePo.setTableName(MovieRankEnum.getMovieRankEnum(movieRankPagePo.getRankType()).getTableName());

        Page<MovieRankDo> movieDetailDos = movieRankMapper.selectMovieRanksByPage(movieRankPagePo);

        return ConvertUtil.pageConvert(movieDetailDos,MovieVo.class);

    }

}
