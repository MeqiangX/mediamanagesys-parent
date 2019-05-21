package com.mingkai.ucserviceapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Created by 云风 on 2019-04-10 16:19
 */
@RestController
public class HelloController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

}
