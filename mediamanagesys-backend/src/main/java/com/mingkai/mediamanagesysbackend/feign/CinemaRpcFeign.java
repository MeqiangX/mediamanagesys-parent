package com.mingkai.mediamanagesysbackend.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.web.R;
import com.mingkai.mediamanagesysmapper.common.API;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.*;
import com.mingkai.mediamanagesysmapper.model.Po.movie.MovieArgBackPo;
import com.mingkai.mediamanagesysmapper.model.Po.movie.MovieArrangePo;
import com.mingkai.mediamanagesysmapper.model.Vo.MapVo;
import com.mingkai.mediamanagesysmapper.model.Vo.cinema.*;
import com.mingkai.mediamanagesysmapper.model.Vo.screen.ScreenRoomVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-25 14:59
 */
@FeignClient(name = "cinema-api")
@Repository
public interface CinemaRpcFeign {

    // ---  影院
    @ApiOperation("影院测试")
    @RequestMapping(value = API.API_CINEMA + "/cinema-test",method = RequestMethod.GET)
    R<Boolean> cinemaTest();

    @ApiOperation("查询影院by id")
    @RequestMapping(value = API.API_CINEMA + "/search-cinema-by-id",method = RequestMethod.GET)
    R<CinemaVo> searchCinemaById(@RequestParam("cinemaId") String cinemaId);

    @ApiOperation("删除影院by id")
    @RequestMapping(value = API.API_CINEMA + "/del-by-id",method = RequestMethod.GET)
    R<Boolean> deleteCinemaById(@RequestParam("cinemaId") Integer cinemaId);

    @ApiOperation("查询影院by电影")
    @RequestMapping(value = API.API_CINEMA + "/cinema-by-movie",method = RequestMethod.POST)
    R<Page<CinemaVo>> cinemaByMovie(@RequestBody CinemaPagePo cinemaPagePo);

    @ApiOperation("添加(修改)地区影院")
    @RequestMapping(value = API.API_CINEMA + "/cinema-add",method = RequestMethod.POST)
    R<Boolean> cinemaAdd(@RequestBody CinemaAddPo cinemaAddPo);


    @ApiOperation("影院配置放映厅")
    @RequestMapping(value = API.API_CINEMA + "/cinema-screen-config",method = RequestMethod.POST)
    R<Boolean> cinemaScreenConfig(@RequestBody CinemaScreenUpdatePo cinemaScreenUpdatePo);


    @ApiOperation("排片")
    @RequestMapping(value = API.API_CINEMA + "/cinema-movie-arrange",method = RequestMethod.POST)
    R<Boolean> cinemaMovieArrange(@RequestBody MovieArrangePo movieArrangePo);


    @ApiOperation("根据地域id来查找影院")
    @RequestMapping(value = API.API_CINEMA + "/find-cinema-by-areaId",method = RequestMethod.POST)
    R<Page<CinemaVo>> searchCinemas(@RequestBody CinemaSearchPo cinemaSearchPo);


    @ApiOperation("查找区域下的所有电影")
    @RequestMapping(value = API.API_CINEMA + "/find-all-cinema-areaId",method = RequestMethod.GET)
    R<List<CinemaVo>> findAllCinemasAreaId(@RequestParam("areaId") Integer areaId);


    // 影院下的排片记录
    @ApiOperation("查找影院下排片信息")
    @RequestMapping(value = API.API_CINEMA + "/cinema-under-movies",method = RequestMethod.GET)
    R<List<MovieArgUnderCinemaVo>> cinemaUnderMovies(@RequestParam("cinemaId") String cinemaId);

    @ApiOperation("删除排片记录")
    @RequestMapping(value = API.API_CINEMA + "/delete-arrange-record",method = RequestMethod.GET)
    R<Boolean> deleteArrangeRecord(@RequestParam("arrangeId") Integer arrangeId);

    @ApiOperation("根据排片id查找对应的信息(选座)")
    @RequestMapping(value = API.API_CINEMA + "/arrange-details/{arrangeId}")
    R<MovieArrangeDetailVo> arrangeDetails(@PathVariable String arrangeId);


    @ApiOperation("根据排片id 查找对应的座位信息")
    @RequestMapping(value = API.API_CINEMA + "/saets-info/{arrangeId}")
    R<List<ScreenSeatMapVo>> seatsInfo(@PathVariable String arrangeId);


    @ApiOperation("搜索排片记录")
    @RequestMapping(value = API.API_CINEMA + "/arrange-records",method = RequestMethod.POST)
    R<Page<MovieArgVo>> arrangeRecords(@RequestBody MovieArgBackPo movieArgBackPo);


    @ApiOperation("搜索排片记录-by-id")
    @RequestMapping(value = API.API_CINEMA + "/arrange-records-by-id",method = RequestMethod.GET)
    R<MovieArgVo> arrangeRecordById(@RequestParam("id") Integer id);


    @ApiOperation("跟新排片记录")
    @RequestMapping(value = API.API_CINEMA + "/update-arrange-info",method = RequestMethod.POST)
    R<Boolean> updateArrangeInfo(@RequestBody MovieArrangePo movieArrangePo);


    @ApiOperation("地图数据")
    @GetMapping("maps-data")
    R<List<List<MapVo>>> mapsData();

    // ---  放映厅

    @ApiOperation("测试查询")
    @RequestMapping(value = API.API_SCREEN + "/query-screen-room",method = RequestMethod.GET)
    R<ScreenRoomVo> queryScreenRoom();


    @ApiOperation("通过id查询放映厅")
    @RequestMapping(value = API.API_SCREEN + "/query-screen-by-id",method = RequestMethod.GET)
    R<ScreenRoomVo> queryScreenById(@RequestParam("id") Integer id);

    @ApiOperation("分页查询放映厅")
    @RequestMapping(value = API.API_SCREEN + "/query-screens-page",method = RequestMethod.POST)
    R<Page<ScreenRoomVo>> queryScreensPage(@RequestBody ScreenPagePo screenPagePo);

    @ApiOperation("影院id来查询下面的所有放映厅")
    @RequestMapping(value = API.API_SCREEN + "/query-screens-by-cinemaId",method = RequestMethod.GET)
    R<List<ScreenRoomVo>> queryScreensByCinemaId(@RequestParam("cinemaId") Integer cinemaId);


    @ApiOperation("影院id来查询下面的放映厅-分页")
    @RequestMapping(value = API.API_SCREEN + "/querypage-screens-by-cinemaId",method = RequestMethod.POST)
    R<Page<ScreenRoomVo>> queryPageScreensByCinemaId(@RequestBody ScreenPagePo screenPagePo);

    @ApiOperation("影院id来查询可以配置的放映厅-分页")
    @RequestMapping(value = API.API_SCREEN + "/querypage-canConfig-screens-by-cinemaId",method = RequestMethod.POST)
    R<Page<ScreenRoomVo>> queryPageCanConfigScreensByCinemaId(@RequestBody ScreenPagePo screenPagePo);

    @ApiOperation("添加放映厅")
    @RequestMapping(value = API.API_SCREEN + "/add-screen-room",method = RequestMethod.POST)
    R<Boolean> addScreenRoom(@RequestBody ScreenRoomAddPo screenRoomAddPo);

    @ApiOperation("当前放映厅是否排片")
    @RequestMapping(value = API.API_SCREEN + "/is-arranged",method = RequestMethod.GET)
    R<Boolean> isArranged(@RequestParam("id") Integer id);


    @ApiOperation("通过id来删除放映厅")
    @RequestMapping(value = API.API_SCREEN + "/delete-screen-by-id",method = RequestMethod.GET)
    R<Boolean> deleteScreenById(@RequestParam("id") Integer id);
}
