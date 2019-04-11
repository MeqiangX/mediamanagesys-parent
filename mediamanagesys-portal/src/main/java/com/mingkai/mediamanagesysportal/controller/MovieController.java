package com.mingkai.mediamanagesysportal.controller;

import com.dtstack.plat.lang.base.Checks;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.common.API;
import com.mingkai.mediamanagesyscommon.model.Vo.movie.MovieVo;
import com.mingkai.mediamanagesysportal.service.MovieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-10 17:56
 */
@RestController
@RequestMapping(API.API_MOVIE)
@Api(tags = API.PROTAL_MOVIE)
public class MovieController {

    @Autowired
    private MovieService movieService;

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
                return movieService.movieDetailById(id);
            }
        }.execute();
    }



}
