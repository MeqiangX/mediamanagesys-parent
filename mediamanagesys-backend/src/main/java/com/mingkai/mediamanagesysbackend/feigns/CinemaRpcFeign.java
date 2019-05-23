package com.mingkai.mediamanagesysbackend.feigns;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.web.R;
import com.mingkai.mappermodule.model.Po.MovieArgBackPo;
import com.mingkai.mappermodule.model.Po.MovieArrangePo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaAddPo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaPagePo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaScreenUpdatePo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaSearchPo;
import com.mingkai.mappermodule.model.Vo.cinema.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-14 22:28
 */
@FeignClient(value = "cinema-service-api")
@Repository
public interface CinemaRpcFeign {

    @RequestMapping(value = "cinema-test",method = RequestMethod.GET)
    R<Boolean> cinemaTest();

    @RequestMapping(value = "search-cinema-by-id",method = RequestMethod.GET)
    R<CinemaVo> searchCinemaById(@RequestParam("cinemaId") String cinemaId);


    @RequestMapping(value = "del-by-id",method = RequestMethod.GET)
    R<Boolean> deleteCinemaById(@RequestParam("cinemaId") Integer cinemaId);

    @ApiOperation("查询影院by电影")
    @RequestMapping(value = "cinema-by-movie",method = RequestMethod.POST)
    R<Page<CinemaVo>> cinemaByMovie(@RequestBody CinemaPagePo cinemaPagePo);


    @RequestMapping(value = "cinema-add",method = RequestMethod.POST)
    R<Boolean> cinemaAdd(@RequestBody CinemaAddPo cinemaAddPo);


    @RequestMapping(value = "cinema-screen-config",method = RequestMethod.POST)
    R<Boolean> cinemaScreenConfig(@RequestBody CinemaScreenUpdatePo cinemaScreenUpdatePo);



    @RequestMapping(value = "cinema-movie-arrange",method = RequestMethod.POST)
    R<Boolean> cinemaMovieArrange(@RequestBody MovieArrangePo movieArrangePo);


    @RequestMapping(value = "find-cinema-by-areaId",method = RequestMethod.POST)
    R<Page<CinemaVo>> searchCinemas(@RequestBody CinemaSearchPo cinemaSearchPo);


    @RequestMapping(value = "find-all-cinema-areaId",method = RequestMethod.GET)
    R<List<CinemaVo>> findAllCinemasAreaId(@RequestParam("areaId") Integer areaId);



    @RequestMapping(value = "cinema-under-movies",method = RequestMethod.GET)
    R<List<MovieArgUnderCinemaVo>> cinemaUnderMovies(@RequestParam("cinemaId") String cinemaId);


    @RequestMapping(value = "/delete-arrange-record",method = RequestMethod.GET)
    R<Boolean> deleteArrangeRecord(@RequestParam("arrangeId") Integer arrangeId);


    @RequestMapping(value = "arrange-details/{arrangeId}")
    R<MovieArrangeDetailVo> arrangeDetails(@PathVariable String arrangeId);



    @RequestMapping(value = "saets-info/{arrangeId}")
    R<List<ScreenSeatMapVo>> seatsInfo(@PathVariable String arrangeId);


    @RequestMapping(value = "arrange-records",method = RequestMethod.POST)
    R<Page<MovieArgVo>> arrangeRecords(@RequestBody MovieArgBackPo movieArgBackPo);


    @RequestMapping(value = "arrange-records-by-id",method = RequestMethod.GET)
    R<MovieArgVo> arrangeRecordById(@RequestParam("id") Integer id);


    @RequestMapping(value = "update-arrange-info",method = RequestMethod.POST)
    R<Boolean> updateArrangeInfo(@RequestBody MovieArrangePo movieArrangePo);
}
