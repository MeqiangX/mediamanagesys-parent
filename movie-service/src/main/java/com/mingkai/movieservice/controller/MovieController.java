package com.mingkai.movieservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-12 11:06
 */
@RestController
public class MovieController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/movie-test")
    public String movieServiceTest(@RequestParam("id") String id){
        System.out.println("进入");
        return "电影服务测试----> I am from " + port + "--" + id;
    }

}
