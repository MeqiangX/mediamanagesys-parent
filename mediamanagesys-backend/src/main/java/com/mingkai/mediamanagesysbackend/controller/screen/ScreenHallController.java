package com.mingkai.mediamanagesysbackend.controller.screen;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesysbackend.model.PO.ScreenArrangePo;
import com.mingkai.mediamanagesysbackend.model.PO.ScreenRoomAddPo;
import com.mingkai.mediamanagesysbackend.service.screen.ScreenService;
import com.mingkai.mediamanagesyscommon.common.API;
import com.mingkai.mediamanagesyscommon.model.Vo.screen.ScreenRoomVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("测试添加放映厅")
    @PostMapping("add-screen-room")
    public R<Boolean> addScreenRoom(@RequestBody ScreenRoomAddPo screenRoomAddPo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return screenService.addScreenRoom(screenRoomAddPo);
            }
        }.execute();
    }

    @ApiOperation("测试排片")
    @PostMapping("arrange-movie")
    public R<Boolean> arrangeMovie(@RequestBody ScreenArrangePo screenArrangePo){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return screenService.arrangeMovie(screenArrangePo);
            }
        }.execute();

    }

}
