package com.mingkai.mediamanagesysbackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mediamanagesysbackend.feign.CinemaRpcFeign;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.*;
import com.mingkai.mediamanagesysmapper.model.Po.movie.MovieArgBackPo;
import com.mingkai.mediamanagesysmapper.model.Po.movie.MovieArrangePo;
import com.mingkai.mediamanagesysmapper.model.Vo.MapVo;
import com.mingkai.mediamanagesysmapper.model.Vo.cinema.*;
import com.mingkai.mediamanagesysmapper.model.Vo.screen.ScreenRoomVo;
import com.mingkai.mediamanagesysmapper.utils.check.CheckOfR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-25 15:21
 */
@Service
@Slf4j
public class CinemaRpcService {

    @Autowired
    private CinemaRpcFeign cinemaRpcFeign;

    // ---  影院
    public Boolean cinemaTest(){
        return CheckOfR.check(cinemaRpcFeign.cinemaTest());
    }


    public CinemaVo searchCinemaById(String cinemaId){
        return CheckOfR.check(cinemaRpcFeign.searchCinemaById(cinemaId));
    }

    public Boolean deleteCinemaById(Integer cinemaId){
        return CheckOfR.check(cinemaRpcFeign.deleteCinemaById(cinemaId));
    }

    public Page<CinemaVo> cinemaByMovie(CinemaPagePo cinemaPagePo){
        return CheckOfR.check(cinemaRpcFeign.cinemaByMovie(cinemaPagePo));
    }


    public Boolean cinemaAdd(CinemaAddPo cinemaAddPo){
        return CheckOfR.check(cinemaRpcFeign.cinemaAdd(cinemaAddPo));
    }


    public Boolean cinemaScreenConfig(CinemaScreenUpdatePo cinemaScreenUpdatePo){
        return CheckOfR.check(cinemaRpcFeign.cinemaScreenConfig(cinemaScreenUpdatePo));
    }


    public Boolean cinemaMovieArrange(MovieArrangePo movieArrangePo){
        return CheckOfR.check(cinemaRpcFeign.cinemaMovieArrange(movieArrangePo));
    }



    public Page<CinemaVo> searchCinemas(CinemaSearchPo cinemaSearchPo){
        return CheckOfR.check(cinemaRpcFeign.searchCinemas(cinemaSearchPo));
    }


    public List<CinemaVo> findAllCinemasAreaId(Integer areaId){
        return CheckOfR.check(cinemaRpcFeign.findAllCinemasAreaId(areaId));
    }


    // 影院下的排片记录
    public List<MovieArgUnderCinemaVo> cinemaUnderMovies(String cinemaId){
        return CheckOfR.check(cinemaRpcFeign.cinemaUnderMovies(cinemaId));
    }


    public Boolean deleteArrangeRecord(Integer arrangeId){
        return CheckOfR.check(cinemaRpcFeign.deleteArrangeRecord(arrangeId));
    }


    public MovieArrangeDetailVo arrangeDetails(String arrangeId){
        return CheckOfR.check(cinemaRpcFeign.arrangeDetails(arrangeId));
    }

    public List<ScreenSeatMapVo> seatsInfo(String arrangeId){
        return CheckOfR.check(cinemaRpcFeign.seatsInfo(arrangeId));
    }


    public Page<MovieArgVo> arrangeRecords(MovieArgBackPo movieArgBackPo){
        return CheckOfR.check(cinemaRpcFeign.arrangeRecords(movieArgBackPo));
    }



    public MovieArgVo arrangeRecordById(Integer id){
        return CheckOfR.check(cinemaRpcFeign.arrangeRecordById(id));
    }



    public Boolean updateArrangeInfo(MovieArrangePo movieArrangePo){
        return CheckOfR.check(cinemaRpcFeign.updateArrangeInfo(movieArrangePo));
    }


    // ---  放映厅


    public ScreenRoomVo queryScreenRoom(){
        return CheckOfR.check(cinemaRpcFeign.queryScreenRoom());
    }


    public ScreenRoomVo queryScreenById(Integer id){
        return CheckOfR.check(cinemaRpcFeign.queryScreenById(id));
    }


    public Page<ScreenRoomVo> queryScreensPage(ScreenPagePo screenPagePo){
        return CheckOfR.check(cinemaRpcFeign.queryScreensPage(screenPagePo));
    }


    public List<ScreenRoomVo> queryScreensByCinemaId(Integer cinemaId){
        return CheckOfR.check(cinemaRpcFeign.queryScreensByCinemaId(cinemaId));
    }


    public Page<ScreenRoomVo> queryPageScreensByCinemaId(ScreenPagePo screenPagePo){
        return CheckOfR.check(cinemaRpcFeign.queryPageScreensByCinemaId(screenPagePo));
    }


    public Page<ScreenRoomVo> queryPageCanConfigScreensByCinemaId(ScreenPagePo screenPagePo){
        return CheckOfR.check(cinemaRpcFeign.queryPageCanConfigScreensByCinemaId(screenPagePo));
    }

    public Boolean addScreenRoom(ScreenRoomAddPo screenRoomAddPo){
        return CheckOfR.check(cinemaRpcFeign.addScreenRoom(screenRoomAddPo));
    }


    public Boolean isArranged(Integer id){
        return CheckOfR.check(cinemaRpcFeign.isArranged(id));
    }


    public Boolean deleteScreenById(Integer id){
        return CheckOfR.check(cinemaRpcFeign.deleteScreenById(id));
    }

    public List<List<MapVo>> mapsData(){
        return CheckOfR.check(cinemaRpcFeign.mapsData());
    }
}
