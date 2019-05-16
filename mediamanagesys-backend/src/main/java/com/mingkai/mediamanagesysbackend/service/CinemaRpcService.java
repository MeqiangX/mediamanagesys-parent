package com.mingkai.mediamanagesysbackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mappermodule.model.Po.MovieArgBackPo;
import com.mingkai.mappermodule.model.Po.MovieArrangePo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaAddPo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaPagePo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaScreenUpdatePo;
import com.mingkai.mappermodule.model.Po.cinema.CinemaSearchPo;
import com.mingkai.mappermodule.model.Vo.cinema.*;
import com.mingkai.mappermodule.utils.check.CheckOfR;
import com.mingkai.mediamanagesysbackend.feigns.CinemaRpcFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-14 22:29
 */
@Service
public class CinemaRpcService {

    @Autowired
    private CinemaRpcFeign cinemaRpcFeign;


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

}
