package com.mingkai.mediamanagesysbackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mingkai.mappermodule.model.Po.ScreenRoomAddPo;
import com.mingkai.mappermodule.model.Po.cinema.ScreenPagePo;
import com.mingkai.mappermodule.model.Vo.screen.ScreenRoomVo;
import com.mingkai.mappermodule.utils.check.CheckOfR;
import com.mingkai.mediamanagesysbackend.feigns.ScreenRpcFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-14 22:29
 */
@Service
public class ScreenRpcService {

    @Autowired
    private ScreenRpcFeign screenRpcFeign;


    public ScreenRoomVo queryScreenRoom(){
        return CheckOfR.check(screenRpcFeign.queryScreenRoom());
    }


    public ScreenRoomVo queryScreenById(Integer id){
        return CheckOfR.check(screenRpcFeign.queryScreenById(id));
    }


    public Page<ScreenRoomVo> queryScreensPage(ScreenPagePo screenPagePo){
        return CheckOfR.check(screenRpcFeign.queryScreensPage(screenPagePo));
    }


    public List<ScreenRoomVo> queryScreensByCinemaId(Integer cinemaId){
        return CheckOfR.check(screenRpcFeign.queryScreensByCinemaId(cinemaId));
    }


    public Page<ScreenRoomVo> queryPageScreensByCinemaId(ScreenPagePo screenPagePo){
        return CheckOfR.check(screenRpcFeign.queryPageScreensByCinemaId(screenPagePo));
    }


    public Page<ScreenRoomVo> queryPageCanConfigScreensByCinemaId(ScreenPagePo screenPagePo){
        return CheckOfR.check(screenRpcFeign.queryPageCanConfigScreensByCinemaId(screenPagePo));
    }


    public Boolean addScreenRoom(ScreenRoomAddPo screenRoomAddPo){
        return CheckOfR.check(screenRpcFeign.addScreenRoom(screenRoomAddPo));
    }



    public Boolean isArranged(Integer id){
        return CheckOfR.check(screenRpcFeign.isArranged(id));
    }



    public Boolean deleteScreenById(Integer id){
        return CheckOfR.check(screenRpcFeign.deleteScreenById(id));
    }

}
