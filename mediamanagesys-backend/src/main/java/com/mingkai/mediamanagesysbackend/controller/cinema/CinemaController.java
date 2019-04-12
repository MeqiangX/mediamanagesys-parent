package com.mingkai.mediamanagesysbackend.controller.cinema;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesysbackend.model.PO.MovieArrangePo;
import com.mingkai.mediamanagesysbackend.service.cinema.CinemaService;
import com.mingkai.mediamanagesyscommon.common.API;
import com.mingkai.mediamanagesyscommon.model.Po.cinema.CinemaAddPo;
import com.mingkai.mediamanagesyscommon.model.Po.cinema.CinemaPagePo;
import com.mingkai.mediamanagesyscommon.model.Po.cinema.CinemaScreenUpdatePo;
import com.mingkai.mediamanagesyscommon.model.Vo.cinema.CinemaVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 后台影院Controller
 * @author: Created by 云风 on 2019-04-12 9:04
 */
@RestController
@RequestMapping(API.API_CINEMA)
@Api(tags = API.BACKEND_CINEMA)
public class CinemaController {

    @Autowired
    private CinemaService cinemaService;

    @ApiOperation("影院测试")
    @GetMapping("cinema-test")
    public R<Boolean> cinemaTest(){
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

    @ApiOperation("查询影院by电影")
    @GetMapping("cinema-by-movie")
    public R<Page<CinemaVo>> cinemaByMovie(CinemaPagePo cinemaPagePo){
        return new APITemplate<Page<CinemaVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<CinemaVo> process() throws BizException {
                return cinemaService.cinemaByMovie(cinemaPagePo);
            }
        }.execute();
    }

    @ApiOperation("添加地区影院")
    @PostMapping("cinema-add")
    public R<Boolean> cinemaAdd(CinemaAddPo cinemaAddPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return cinemaService.cinemaAdd(cinemaAddPo);
            }
        }.execute();
    }


    @ApiOperation("影院配置放映厅")
    @PostMapping("cinema-screen-config")
    public R<Boolean> cinemaScreenConfig(CinemaScreenUpdatePo cinemaScreenUpdatePo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return cinemaService.cinemaScreenConfig(cinemaScreenUpdatePo);
            }
        }.execute();
    }


    @ApiOperation("排片")
    @PostMapping("cinema-movie-arrange")
    public R<Boolean> cinemaMovieArrange(MovieArrangePo movieArrangePo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return cinemaService.arrangeMovie(movieArrangePo);
            }
        }.execute();
    }
}
