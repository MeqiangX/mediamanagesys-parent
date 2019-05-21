package com.mingkai.movieserviceapi.controller;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-21 19:27
 */
@RestController
public class MovieZuulTestController {

    @GetMapping("test")
    public R<String> zuulTest(){
        return new APITemplate<String>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected String process() throws BizException {
                return "网关测试";
            }
        }.execute();
    }

}
