package com.mingkai.mediamanagesysportal.controller;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.common.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-12 17:59
 */
@RestController
@RequestMapping(API.API_ALIPAY)
@Api(tags = API.PROTAL_ALIPAY)
public class ApiPayController {

    @ApiOperation("支付测试")
    @PostMapping("test-pay")
    public R<Boolean> payTest(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return null;
            }
        }.execute();
    }

}
