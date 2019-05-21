package com.mingkai.cinemaserviceapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.cinemaserviceapi.service.CinemaService;
import com.mingkai.mappermodule.common.API;
import com.mingkai.mappermodule.model.Po.MovieArgBackPo;
import com.mingkai.mappermodule.model.Po.MovieArrangePo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaAddPo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaPagePo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaScreenUpdatePo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaSearchPo;
import com.mingkai.mappermodule.model.Vo.cinema.*;
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
    public R<CinemaVo> searchCinemaById(@RequestParam("cinemaId") String cinemaId){
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
    public R<Boolean> deleteCinemaById(@RequestParam("cinemaId") Integer cinemaId){
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
    @PostMapping("cinema-by-movie")
    public R<Page<CinemaVo>> cinemaByMovie(@RequestBody CinemaPagePo cinemaPagePo){
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
    public R<Boolean> cinemaMovieArrange(@RequestBody MovieArrangePo movieArrangePo){
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
    @PostMapping("find-cinema-by-areaId")
    public R<Page<CinemaVo>> searchCinemas(@RequestBody CinemaSearchPo cinemaSearchPo){
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


    @ApiOperation("查找区域下的所有电影")
    @GetMapping("find-all-cinema-areaId")
    public R<List<CinemaVo>> findAllCinemasAreaId(@RequestParam("areaId") Integer areaId){
        return new APITemplate<List<CinemaVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<CinemaVo> process() throws BizException {
                return cinemaService.findAllCinemasAreaId(areaId);
            }
        }.execute();
    }


    // 影院下的排片记录
    @ApiOperation("查找影院下排片信息")
    @GetMapping("cinema-under-movies")
    public R<List<MovieArgUnderCinemaVo>> cinemaUnderMovies(@RequestParam("cinemaId") String cinemaId){
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

    @ApiOperation("删除排片记录")
    @GetMapping("/delete-arrange-record")
    public R<Boolean> deleteArrangeRecord(@RequestParam("arrangeId") Integer arrangeId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return cinemaService.deleteArrangeRecord(arrangeId);
            }
        }.execute();
    }

    @ApiOperation("根据排片id查找对应的信息(选座)")
    @RequestMapping("arrange-details/{arrangeId}")
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
    @RequestMapping("saets-info/{arrangeId}")
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


    @ApiOperation("搜索排片记录")
    @PostMapping("arrange-records")
    public R<Page<MovieArgVo>> arrangeRecords(@RequestBody MovieArgBackPo movieArgBackPo){

        return new APITemplate<Page<MovieArgVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<MovieArgVo> process() throws BizException {
                return cinemaService.arrangeRecords(movieArgBackPo);
            }
        }.execute();

    }


    @ApiOperation("搜索排片记录-by-id")
    @GetMapping("arrange-records-by-id")
    public R<MovieArgVo> arrangeRecordById(@RequestParam("id") Integer id){

        return new APITemplate<MovieArgVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected MovieArgVo process() throws BizException {
                return cinemaService.arrangeRecordById(id);
            }
        }.execute();

    }


    @ApiOperation("跟新排片记录")
    @PostMapping("update-arrange-info")
    public R<Boolean> updateArrangeInfo(@RequestBody MovieArrangePo movieArrangePo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return cinemaService.updateArrangeInfo(movieArrangePo);
            }
        }.execute();
    }


}
