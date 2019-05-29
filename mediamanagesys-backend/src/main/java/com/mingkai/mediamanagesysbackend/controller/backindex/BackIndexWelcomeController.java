package com.mingkai.mediamanagesysbackend.controller.backindex;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesysbackend.service.backindex.BackIndexWelcomeService;
import com.mingkai.mediamanagesysmapper.common.API;
import com.mingkai.mediamanagesysmapper.model.Po.EchartsPo;
import com.mingkai.mediamanagesysmapper.model.Vo.EchartsVo;
import com.mingkai.mediamanagesysmapper.model.Vo.PaneDataVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @description:  后台数据统计
 * @author: Created by 云风 on 2019-05-04 19:59
 */
@RestController
@Api(tags = API.BACKEND_WELCOME)
@RequestMapping(API.API_WELCOME)
public class BackIndexWelcomeController {

    @Autowired
    private BackIndexWelcomeService backIndexWelcomeService;

    @ApiOperation("6个面板数据")
    @GetMapping("/pane-data")
    public R<List<PaneDataVo>> paneData(){
        return new APITemplate<List<PaneDataVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<PaneDataVo> process() throws BizException {
                return backIndexWelcomeService.paneData();
            }
        }.execute();
    }

    @ApiOperation("4个Echarts图表数据")
    @GetMapping("/echarts-option")
    public R<List<EchartsVo>> echartsOption(EchartsPo echartsPo){
        return new APITemplate<List<EchartsVo>>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected List<EchartsVo> process() throws BizException {
                return backIndexWelcomeService.echartsOptionBetweenDate(echartsPo);
            }
        }.execute();
    }

}
