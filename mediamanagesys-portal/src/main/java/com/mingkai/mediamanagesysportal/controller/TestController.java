package com.mingkai.mediamanagesysportal.controller;

import com.dtstack.plat.lang.exception.BizException;
import com.dtstack.plat.lang.web.R;
import com.dtstack.plat.lang.web.template.APITemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: Created by 云风 on 2019-03-30 16:33
 */


@Controller
public class TestController {

    @RequestMapping("/test2")
    public String test(){
        return "index";
    }

    @GetMapping("test")
    public R<String> zuulTest(){
        return new APITemplate<String>() {
            @Override
            protected void checkParams() throws IllegalArgumentException {

            }

            @Override
            protected String process() throws BizException {
                return null;
            }
        }.execute();
    }

}
