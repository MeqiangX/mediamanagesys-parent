package com.mingkai.mediamanagesysbackend.feigns;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.web.R;
import com.mingkai.mappermodule.model.Po.ScreenRoomAddPo;
import com.mingkai.mappermodule.model.Po.cinema.ScreenPagePo;
import com.mingkai.mappermodule.model.Vo.screen.ScreenRoomVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-14 22:29
 */
@FeignClient(value = "screen-service-api")
@Repository
public interface ScreenRpcFeign {

    @ApiOperation("测试查询")
    @GetMapping("query-screen-room")
    R<ScreenRoomVo> queryScreenRoom();


    @RequestMapping(value = "/query-screen-by-id",method = RequestMethod.GET)
    R<ScreenRoomVo> queryScreenById(@RequestParam("id") Integer id);


    @RequestMapping(value = "query-screens-page",method = RequestMethod.POST)
    R<Page<ScreenRoomVo>> queryScreensPage(@RequestBody ScreenPagePo screenPagePo);


    @RequestMapping(value = "query-screens-by-cinemaId",method = RequestMethod.GET)
    R<List<ScreenRoomVo>> queryScreensByCinemaId(@RequestParam("cinemaId") Integer cinemaId);


    @RequestMapping(value = "querypage-screens-by-cinemaId",method = RequestMethod.POST)
    R<Page<ScreenRoomVo>> queryPageScreensByCinemaId(@RequestBody ScreenPagePo screenPagePo);

    @RequestMapping(value = "querypage-canConfig-screens-by-cinemaId",method = RequestMethod.POST)
    R<Page<ScreenRoomVo>> queryPageCanConfigScreensByCinemaId(@RequestBody ScreenPagePo screenPagePo);


    @RequestMapping(value = "add-screen-room",method = RequestMethod.POST)
    R<Boolean> addScreenRoom(@RequestBody ScreenRoomAddPo screenRoomAddPo);


    @RequestMapping(value = "is-arranged",method = RequestMethod.GET)
    R<Boolean> isArranged(@RequestParam("id") Integer id);


    @RequestMapping(value = "delete-screen-by-id",method = RequestMethod.GET)
    R<Boolean> deleteScreenById(@RequestParam("id") Integer id);

}
