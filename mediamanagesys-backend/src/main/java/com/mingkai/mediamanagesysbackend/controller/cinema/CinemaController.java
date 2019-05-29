package com.mingkai.mediamanagesysbackend.controller.cinema;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesysbackend.service.CinemaRpcService;
import com.mingkai.mediamanagesysbackend.service.cinema.CinemaService;
import com.mingkai.mediamanagesysmapper.common.API;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.CinemaAddPo;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.CinemaPagePo;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.CinemaScreenUpdatePo;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.CinemaSearchPo;
import com.mingkai.mediamanagesysmapper.model.Po.movie.MovieArgBackPo;
import com.mingkai.mediamanagesysmapper.model.Po.movie.MovieArrangePo;
import com.mingkai.mediamanagesysmapper.model.Vo.MapVo;
import com.mingkai.mediamanagesysmapper.model.Vo.PercentPaneVo;
import com.mingkai.mediamanagesysmapper.model.Vo.cinema.*;
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
    private CinemaRpcService cinemaRpcService;

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
                return cinemaRpcService.searchCinemaById(cinemaId);
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
                return cinemaRpcService.deleteCinemaById(cinemaId);
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
                return cinemaRpcService.cinemaByMovie(cinemaPagePo);
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
                return cinemaRpcService.cinemaAdd(cinemaAddPo);
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
                return cinemaRpcService.cinemaScreenConfig(cinemaScreenUpdatePo);
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
                return cinemaRpcService.cinemaMovieArrange(movieArrangePo);
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
                return cinemaRpcService.searchCinemas(cinemaSearchPo);
            }
        }.execute();
    }


    @ApiOperation("查找区域下的所有电影")
    @GetMapping("find-all-cinema-areaId")
    public R<List<CinemaVo>> findAllCinemasAreaId(Integer areaId){
        return new APITemplate<List<CinemaVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<CinemaVo> process() throws BizException {
                return cinemaRpcService.findAllCinemasAreaId(areaId);
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
                return cinemaRpcService.cinemaUnderMovies(cinemaId);
            }
        }.execute();
    }

    @ApiOperation("删除排片记录")
    @GetMapping("/delete-arrange-record")
    public R<Boolean> deleteArrangeRecord(Integer arrangeId){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return cinemaRpcService.deleteArrangeRecord(arrangeId);
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
                return cinemaRpcService.arrangeDetails(arrangeId);
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
                return cinemaRpcService.seatsInfo(arrangeId);
            }
        }.execute();
    }


    @ApiOperation("搜索排片记录")
    @GetMapping("arrange-records")
    public R<Page<MovieArgVo>> arrangeRecords(MovieArgBackPo movieArgBackPo){

        return new APITemplate<Page<MovieArgVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<MovieArgVo> process() throws BizException {
                return cinemaRpcService.arrangeRecords(movieArgBackPo);
            }
        }.execute();

    }


    @ApiOperation("搜索排片记录-by-id")
    @GetMapping("arrange-records-by-id")
    public R<MovieArgVo> arrangeRecordById(Integer id){

        return new APITemplate<MovieArgVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected MovieArgVo process() throws BizException {
                return cinemaRpcService.arrangeRecordById(id);
            }
        }.execute();

    }


    @ApiOperation("跟新排片记录")
    @PostMapping("update-arrange-info")
    public R<Boolean> updateArrangeInfo(MovieArrangePo movieArrangePo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return cinemaRpcService.updateArrangeInfo(movieArrangePo);
            }
        }.execute();
    }


    @ApiOperation("地图数据")
    @GetMapping("maps-data")
    public R<List<List<MapVo>>> mapsData(){
        return new APITemplate<List<List<MapVo>>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<List<MapVo>> process() throws BizException {
                return cinemaService.selectCinemaCountsProvs();
            }
        }.execute();
    }


    /**
     *  三个变量  一个是柱状图的坐标轴 一个是坐标轴的 名称和数值的对应数组 还有一个是饼图的 影院省份分布
     * @return
     */
    @ApiOperation("截止到目前的前十交易额的影院柱狀圖和的省份占比")
    @GetMapping("percent")
    public R<PercentPaneVo> percentTen(){
        return new APITemplate<PercentPaneVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected PercentPaneVo process() throws BizException {
                return cinemaService.percentTen();
            }
        }.execute();
    }

}
