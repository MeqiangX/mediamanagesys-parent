package com.mingkai.mediamanagesysportal.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.mingkai.mediamanagesyscommon.common.MovieRankEnum;
import com.mingkai.mediamanagesyscommon.manager.*;
import com.mingkai.mediamanagesyscommon.mapper.MovieRankMapper;
import com.mingkai.mediamanagesyscommon.model.Do.movie.*;
import com.mingkai.mediamanagesyscommon.model.Po.movie.MoviePagePo;
import com.mingkai.mediamanagesyscommon.model.Po.movie.MovieRankPagePo;
import com.mingkai.mediamanagesyscommon.model.Vo.movie.MovieBlooperVo;
import com.mingkai.mediamanagesyscommon.model.Vo.movie.MovieTrailerVo;
import com.mingkai.mediamanagesyscommon.model.Vo.movie.MovieVo;
import com.mingkai.mediamanagesyscommon.utils.convert.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private MovieDirectorManager movieDirectorManager;

    @Autowired
    private MovieWriterManager movieWriterManager;

    @Autowired
    private MovieCastManager movieCastManager;

    @Autowired
    private MovieTrailerManager movieTrailerManager;

    @Autowired
    private MovieBlooperManager movieBlooperManager;

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

        MovieVo movieVo = ConvertUtil.convert(movieDo, MovieVo.class);

        // 根据 电影关联的 导演ids 查询 导演list
        String[] directorIds = movieVo.getDirectors().split(",");
        List<MovieDirectorDo> directorDoList = movieDirectorManager.list(new QueryWrapper<MovieDirectorDo>()
                .in("director_id", directorIds));
        movieVo.setDirectorList(directorDoList);

        // 根据 电影关联的 编剧ids 查询 编剧list
        String[] writerIds = movieVo.getWriters().split(",");
        List<MovieWriterDo> writerDoList = movieWriterManager.list(new QueryWrapper<MovieWriterDo>()
                .in("writer_id", writerIds));
        movieVo.setWriterList(writerDoList);

        // 根据 电影关联的 演员ids 查询 演员list
        String[] castIds = movieVo.getCasts().split(",");
        List<MovieCastDo> actorDoList = movieCastManager.list(new QueryWrapper<MovieCastDo>()
                .in("actor_id", castIds));
        movieVo.setCastList(actorDoList);

        return movieVo;
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


    /**
     * 电影预告片
     * @param movieId
     * @return
     */
    public List<MovieTrailerVo> movieTrailers(String movieId){

        List<MovieTrailerDo> movieTrailerDoList = movieTrailerManager.list(new QueryWrapper<MovieTrailerDo>()
                .eq("movie_id", movieId));

        return ConvertUtil.listConvert(movieTrailerDoList,MovieTrailerVo.class);

    }


    /**
     * 电影花絮
     * @param movieId
     * @return
     */
    public List<MovieBlooperVo> movieBloopers(String movieId){
        List<MovieBlooperDo> movieBlooperDos = movieBlooperManager.list(new QueryWrapper<MovieBlooperDo>()
                .eq("movie_id", movieId));
        return ConvertUtil.listConvert(movieBlooperDos,MovieBlooperVo.class);
    }


    /**
     * 电影页
     * @param moviePagePo
     * @return
     */
    public Page<MovieVo> moviePage(MoviePagePo moviePagePo){

        Page<MovieDetailDo> movieDetailDoPage = movieDetailManager.getBaseMapper().moviePage(moviePagePo);

        return ConvertUtil.pageConvert(movieDetailDoPage,MovieVo.class);

    }


}
