package com.mingkai.mediamanagesysportal.controller;

import com.mingkai.mediamanagesyscommon.common.API;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Created by 云风 on 2019-03-30 16:33
 */
@RestController
@RequestMapping(value = "test")
@Api(tags = API.TEST)
public class TestController {

    @GetMapping(value = "request")
    public String test(){
        return "success";
    }

}
