package com.mingkai.mediamanagesysportal.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.base.Checks;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mappermodule.common.API;
import com.mingkai.mappermodule.model.Po.movie.MoviePagePo;
import com.mingkai.mappermodule.model.Po.movie.MovieRankPagePo;
import com.mingkai.mappermodule.model.Po.movie.MovieSearchPo;
import com.mingkai.mappermodule.model.Vo.movie.MovieBlooperVo;
import com.mingkai.mappermodule.model.Vo.movie.MovieTrailerVo;
import com.mingkai.mappermodule.model.Vo.movie.MovieVo;
import com.mingkai.mediamanagesysportal.service.MovieRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-10 17:56
 */
@RestController
@RequestMapping(API.API_MOVIE)
@Api(tags = API.PROTAL_MOVIE)
public class MovieController {

    @Autowired
    private MovieRpcService movieRpcService;

    @ApiOperation("前台测试")
    @GetMapping("portal-test")
    public R<Boolean> portalTest(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return Boolean.TRUE;
            }
        }.execute();
    }

    @ApiOperation("电影详情")
    @GetMapping("movie-details/{id}")
    public R<MovieVo> movieDetailsById(@PathVariable String id){
        return new APITemplate<MovieVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {
                Checks.nonNull(id);
            }

            @Override
            protected MovieVo process() throws BizException {
                return movieRpcService.movieDetailsById(id);
            }
        }.execute();
    }


    @ApiOperation("电影榜单")
    @GetMapping("movie-rank")
    public R<Page<MovieVo>> movieRank(MovieRankPagePo movieRankPagePo){
        return new APITemplate<Page<MovieVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<MovieVo> process() throws BizException {
                return movieRpcService.movieRank(movieRankPagePo);
            }
        }.execute();
    }

    @ApiOperation("电影页")
    @GetMapping("movie-page")
    public R<Page<MovieVo>> moviePage(MoviePagePo moviePagePo){
        return new APITemplate<Page<MovieVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<MovieVo> process() throws BizException {
                return movieRpcService.moviePage(moviePagePo);
            }
        }.execute();
    }


    @ApiOperation("得到电影的预告片")
    @GetMapping("movie-trailers/{movieId}")
    public R<List<MovieTrailerVo>> movieTrailers(@PathVariable String movieId){
        return new APITemplate<List<MovieTrailerVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<MovieTrailerVo> process() throws BizException {
                return movieRpcService.movieTrailers(movieId);
            }
        }.execute();
    }

    @ApiOperation("得到电影的花絮")
    @GetMapping("movie-bloopers/{movieId}")
    public R<List<MovieBlooperVo>> movieBloopers(@PathVariable String movieId){
        return new APITemplate<List<MovieBlooperVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<MovieBlooperVo> process() throws BizException {
                return movieRpcService.movieBloopers(movieId);
            }
        }.execute();
    }


    @ApiOperation("模糊搜索电影(电影名，演员，导演)")
    @GetMapping("search-movies")
    public R<Page<MovieVo>> searchMovies(MovieSearchPo movieSearchPo){
        return new APITemplate<Page<MovieVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<MovieVo> process() throws BizException {
                return movieRpcService.searchMovies(movieSearchPo);
            }
        }.execute();
    }

}
