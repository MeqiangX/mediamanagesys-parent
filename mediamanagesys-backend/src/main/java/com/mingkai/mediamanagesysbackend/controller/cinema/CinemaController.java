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
import com.mingkai.mediamanagesyscommon.model.Po.cinema.CinemaSearchPo;
import com.mingkai.mediamanagesyscommon.model.Vo.cinema.CinemaVo;
import com.mingkai.mediamanagesyscommon.model.Vo.cinema.MovieArgUnderCinemaVo;
import com.mingkai.mediamanagesyscommon.model.Vo.cinema.MovieArrangeDetailVo;
import com.mingkai.mediamanagesyscommon.model.Vo.cinema.ScreenSeatMapVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("查询影院by id")
    @GetMapping("search-cinema-by-id")
    public R<CinemaVo> searchCinemaById(String cinemaId){
        return new APITemplate<CinemaVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected CinemaVo process() throws BizException {
                return cinemaService.searchCinemaById(cinemaId);
            }
        }.execute();
    }

    @ApiOperation("删除影院by id")
    @GetMapping("del-by-id")
    public R<Boolean> deleteCinemaById(Integer cinemaId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return cinemaService.deleteCinemaById(cinemaId);
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

    @ApiOperation("添加(修改)地区影院")
    @PostMapping("cinema-add")
    public R<Boolean> cinemaAdd(@RequestBody CinemaAddPo cinemaAddPo){
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
    public R<Boolean> cinemaScreenConfig(@RequestBody CinemaScreenUpdatePo cinemaScreenUpdatePo){
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


    @ApiOperation("根据地域id来查找影院")
    @GetMapping("find-cinema-by-areaId")
    public R<Page<CinemaVo>> searchCinemas(CinemaSearchPo cinemaSearchPo){
        return new APITemplate<Page<CinemaVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<CinemaVo> process() throws BizException {
                return cinemaService.searchCinemas(cinemaSearchPo);
            }
        }.execute();
    }

    // 影院下的排片记录
    @ApiOperation("查找影院下排片信息")
    @GetMapping("cinema-under-movies")
    public R<List<MovieArgUnderCinemaVo>> cinemaUnderMovies(String cinemaId){
        return new APITemplate<List<MovieArgUnderCinemaVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<MovieArgUnderCinemaVo> process() throws BizException {
                return cinemaService.cinemaUnderMovies(cinemaId);
            }
        }.execute();
    }


    @ApiOperation("根据排片id查找对应的信息(选座)")
    @GetMapping("arrange-details/{arrangeId}")
    public R<MovieArrangeDetailVo> arrangeDetails(@PathVariable String arrangeId){
        return new APITemplate<MovieArrangeDetailVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected MovieArrangeDetailVo process() throws BizException {
                return cinemaService.arrangeDetails(arrangeId);
            }
        }.execute();
    }


    @ApiOperation("根据排片id 查找对应的座位信息")
    @GetMapping("saets-info/{arrangeId}")
    public R<List<ScreenSeatMapVo>> seatsInfo(@PathVariable String arrangeId){
        return new APITemplate<List<ScreenSeatMapVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<ScreenSeatMapVo> process() throws BizException {
                return cinemaService.seatsInfo(arrangeId);
            }
        }.execute();
    }

}
