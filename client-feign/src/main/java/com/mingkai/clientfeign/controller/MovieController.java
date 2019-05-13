package com.mingkai.clientfeign.controller;

import com.dtstack.plat.lang.web.R;
import com.mingkai.clientfeign.feign.MovieApiRpcService;
import com.mingkai.clientfeign.feign.MovieFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Created by 云风 on 2019-05-12 13:11
 */
@RestController
public class MovieController {

    @Autowired
    private MovieFeignService movieFeignService;

    @Autowired
    private MovieApiRpcService movieApiRpcService;

    @GetMapping("/movie-test")
    public String getMovieFromFeign(@RequestParam("id") String id){
        return movieFeignService.getMovieTestFromService(id);
    }

    @GetMapping("portal-test")
    public R<Boolean> portalTest(){
        return movieApiRpcService.portalTest();
    }

}
