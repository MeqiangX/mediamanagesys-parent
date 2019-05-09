package com.mingkai.mediamanagesysportal.controller;

import org.springframework.stereotype.Controller;
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

}
