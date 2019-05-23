package com.mingkai.mediamanagesysbackend.controller.screen;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtstack.plat.lang.base.Checks;
import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesysbackend.model.PO.ScreenRoomAddPo;
import com.mingkai.mediamanagesysbackend.service.screen.ScreenService;
import com.mingkai.mediamanagesysmapper.common.API;
import com.mingkai.mediamanagesysmapper.model.Po.cinema.ScreenPagePo;
import com.mingkai.mediamanagesysmapper.model.Vo.screen.ScreenRoomVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 放映厅相关Controller
 * @author: Created by 云风 on 2019-04-03 17:31
 */
@RestController
@RequestMapping(API.API_SCREEN)
@Api(tags = API.BACKEND_SCREEN)
public class ScreenHallController {

    @Autowired
    private ScreenService screenService;

    @ApiOperation("测试查询")
    @GetMapping("query-screen-room")
    public R<ScreenRoomVo> queryScreenRoom(){
        return new APITemplate<ScreenRoomVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected ScreenRoomVo process() throws BizException {
                return screenService.queryScreenRoom();
            }
        }.execute();
    }


    @ApiOperation("通过id查询放映厅")
    @GetMapping("/query-screen-by-id")
    public R<ScreenRoomVo> queryScreenById(Integer id){
        return new APITemplate<ScreenRoomVo>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected ScreenRoomVo process() throws BizException {
                return screenService.queryScreenById(id);
            }
        }.execute();
    }

    @ApiOperation("分页查询放映厅")
    @GetMapping("query-screens-page")
    public R<Page<ScreenRoomVo>> queryScreensPage(ScreenPagePo screenPagePo){
        return new APITemplate<Page<ScreenRoomVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<ScreenRoomVo> process() throws BizException {
                return screenService.queryScreenRoomPage(screenPagePo);
            }
        }.execute();
    }

    @ApiOperation("影院id来查询下面的所有放映厅")
    @GetMapping("query-screens-by-cinemaId")
    public R<List<ScreenRoomVo>> queryScreensByCinemaId(Integer cinemaId){
        return new APITemplate<List<ScreenRoomVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<ScreenRoomVo> process() throws BizException {
                return screenService.queryScreensByCinemaId(cinemaId);
            }
        }.execute();
    }


    @ApiOperation("影院id来查询下面的放映厅-分页")
    @GetMapping("querypage-screens-by-cinemaId")
    public R<Page<ScreenRoomVo>> queryPageScreensByCinemaId(ScreenPagePo screenPagePo){
        return new APITemplate<Page<ScreenRoomVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<ScreenRoomVo> process() throws BizException {
                return screenService.queryPageScreensByCinemaId(screenPagePo);
            }
        }.execute();
    }

    @ApiOperation("影院id来查询可以配置的放映厅-分页")
    @GetMapping("querypage-canConfig-screens-by-cinemaId")
    public R<Page<ScreenRoomVo>> queryPageCanConfigScreensByCinemaId(ScreenPagePo screenPagePo){
        return new APITemplate<Page<ScreenRoomVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Page<ScreenRoomVo> process() throws BizException {
                return screenService.queryPageCanConfigScreensByCinemaId(screenPagePo);
            }
        }.execute();
    }

    @ApiOperation("添加放映厅")
    @PostMapping("add-screen-room")
    public R<Boolean> addScreenRoom(@RequestBody ScreenRoomAddPo screenRoomAddPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {
                Checks.nonNull(screenRoomAddPo);
            }

            @Override
            protected Boolean process() throws BizException {
                return screenService.addScreenRoom(screenRoomAddPo);
            }
        }.execute();
    }

    @ApiOperation("当前放映厅是否排片")
    @GetMapping("is-arranged")
    public R<Boolean> isArranged(Integer id){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return screenService.isArranged(id);
            }
        }.execute();
    }


    @ApiOperation("通过id来删除放映厅")
    @GetMapping("delete-screen-by-id")
    public R<Boolean> deleteScreenById(Integer id){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return screenService.deleteScreenById(id);
            }
        }.execute();
    }

}
