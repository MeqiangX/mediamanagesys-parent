package com.mingkai.mediamanagesysbackend.controller;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.common.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-03 16:15
 */
@RestController
@RequestMapping(API.API_TEST)
@Api(tags = API.TEST)
public class TestController {

    @ApiOperation("接口测试")
    @GetMapping("test")
    public R<Boolean> test(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return new Boolean(true);
            }
        }.execute();
    }

}
