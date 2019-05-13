package com.mingkai.movieserviceapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-12 18:43
 */
@RestController
public class MovieApiController {

    @Value("${server.port}")
    private String port;

    @GetMapping("movie-api")
    public String movieApi(@RequestParam("name") String name){
        return " I am from " + port + " name : " + name;
    }

}
