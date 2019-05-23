package com.mingkai.mediamanagesysportal.feigns;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.web.R;
import com.mingkai.mappermodule.common.API;
import com.mingkai.mappermodule.model.Po.movie.MoviePagePo;
import com.mingkai.mappermodule.model.Po.movie.MovieRankPagePo;
import com.mingkai.mappermodule.model.Po.movie.MovieSearchPo;
import com.mingkai.mappermodule.model.Vo.movie.MovieBlooperVo;
import com.mingkai.mappermodule.model.Vo.movie.MovieTrailerVo;
import com.mingkai.mappermodule.model.Vo.movie.MovieVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-13 21:56
 */
@FeignClient(value = "movie-service-api")
@Repository
public interface MovieRpcFeign {

    @ApiOperation("前台测试")
    @RequestMapping(value = API.API_MOVIE + "/portal-test",method = RequestMethod.GET)
    R<Boolean> portalTest();

    @ApiOperation("电影详情")
    @RequestMapping(value = API.API_MOVIE + "/movie-details/{id}")
    R<MovieVo> movieDetailsById(@PathVariable String id);


    @ApiOperation("电影榜单")
    @RequestMapping(value = API.API_MOVIE + "/movie-rank",method = RequestMethod.POST)
    R<Page<MovieVo>> movieRank(@RequestBody MovieRankPagePo movieRankPagePo);

    @ApiOperation("电影页")
    @RequestMapping(value = API.API_MOVIE + "/movie-page",method = RequestMethod.POST)
    R<Page<MovieVo>> moviePage(@RequestBody MoviePagePo moviePagePo);


    @ApiOperation("得到电影的预告片")
    @RequestMapping(value = API.API_MOVIE + "/movie-trailers/{movieId}")
    R<List<MovieTrailerVo>> movieTrailers(@PathVariable String movieId);

    @ApiOperation("得到电影的花絮")
    @RequestMapping(value = API.API_MOVIE + "/movie-bloopers/{movieId}")
    R<List<MovieBlooperVo>> movieBloopers(@PathVariable String movieId);


    @ApiOperation("模糊搜索电影(电影名，演员，导演)")
    @RequestMapping(value = API.API_MOVIE + "/search-movies",method = RequestMethod.POST)
    R<Page<MovieVo>> searchMovies(@RequestBody MovieSearchPo movieSearchPo);


}
