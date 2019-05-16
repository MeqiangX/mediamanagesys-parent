/*
package com.mingkai.mediamanagesysbackend.controller.user;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import com.mingkai.mediamanagesyscommon.common.API;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

*/
/**
 * @description: 后台-用户管理
 * @author: Created by 云风 on 2019-04-15 10:06
 *//*

@RestController
@RequestMapping(API.API_USERMANAGE)
@Api(tags = API.BACKEND_USER)
public class UserManageController {

    @ApiOperation("后台-用户测试")
    @GetMapping("backend-user")
    public R<Boolean> backendUser(){
        return new APITemplate<Boolean>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected Boolean process() throws BizException {
                return Boolean.TRUE;
            }
        }.execute();
    }

}
*/
